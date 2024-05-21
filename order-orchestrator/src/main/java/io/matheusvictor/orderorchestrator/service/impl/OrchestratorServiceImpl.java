package io.matheusvictor.orderorchestrator.service.impl;

import io.matheusvictor.orderorchestrator.common.OrchestratorRequestDTO;
import io.matheusvictor.orderorchestrator.common.OrchestratorResponseDTO;
import io.matheusvictor.orderorchestrator.common.OrderStatus;
import io.matheusvictor.orderorchestrator.service.OrchestratorService;
import io.matheusvictor.orderorchestrator.service.WorkFlowStep;
import io.matheusvictor.orderorchestrator.service.WorkFlowStepStatus;
import io.matheusvictor.orderorchestrator.service.Workflow;
import io.matheusvictor.orderorchestrator.steps.InventoryStep;
import io.matheusvictor.orderorchestrator.steps.PaymentStep;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class OrchestratorServiceImpl implements OrchestratorService {

    @Qualifier("payment")
    private final WebClient paymentClient;

    @Qualifier("inventory")
    private final WebClient inventoryClient;

    public OrchestratorServiceImpl(WebClient paymentClient, WebClient inventoryClient) {
        this.paymentClient = paymentClient;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public Mono<OrchestratorResponseDTO> orderProduct(OrchestratorRequestDTO requestDTO) {
        Workflow orderWorkflow = getOrderWorkflow(requestDTO);
        return Flux.fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(WorkFlowStep::process)
                .handle((aBoolean, synchronousSink) -> {
                    if (aBoolean.booleanValue()) synchronousSink.next(true);
                    else synchronousSink.error(new WorkflowException("Order processing failed"));
                })
                .then(Mono.fromCallable(() -> getResponseDTO(requestDTO, OrderStatus.ORDER_COMPLETED)))
                .onErrorResume(ex -> revertOrder(orderWorkflow, requestDTO));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDTO requestDTO) {
        WorkFlowStep paymentStep = new PaymentStep(paymentClient, requestDTO.getPaymentRequestDTO());
        WorkFlowStep inventoryStep = new InventoryStep(inventoryClient, requestDTO.getInventoryRequestDTO());
        return new OrderWorkflow(List.of(paymentStep, inventoryStep));
    }

    private Mono<OrchestratorResponseDTO> revertOrder(final Workflow workflow, final OrchestratorRequestDTO requestDTO) {
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkFlowStepStatus.COMPLETE))
                .flatMap(WorkFlowStep::revert).retry(3)
                .then(Mono.just(getResponseDTO(requestDTO, OrderStatus.ORDER_CANCELLED)));
    }

    private OrchestratorResponseDTO getResponseDTO(OrchestratorRequestDTO requestDTO, OrderStatus orderStatus) {
        OrchestratorResponseDTO responseDTO = new OrchestratorResponseDTO();
        responseDTO.setOrderId(requestDTO.getOrderId());
        responseDTO.setAmount(responseDTO.getAmount());
        responseDTO.setProductId(responseDTO.getProductId());
        responseDTO.setUserId(responseDTO.getUserId());
        responseDTO.setStatus(orderStatus);

        return responseDTO;
    }
}
