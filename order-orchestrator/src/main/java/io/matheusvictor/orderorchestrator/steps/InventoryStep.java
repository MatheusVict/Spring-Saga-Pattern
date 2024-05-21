package io.matheusvictor.orderorchestrator.steps;

import io.matheusvictor.orderorchestrator.common.InventoryRequestDTO;
import io.matheusvictor.orderorchestrator.common.InventoryResponseDTO;
import io.matheusvictor.orderorchestrator.common.InventoryStatus;
import io.matheusvictor.orderorchestrator.service.WorkFlowStep;
import io.matheusvictor.orderorchestrator.service.WorkFlowStepStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class InventoryStep implements WorkFlowStep {

    private final WebClient webClient;
    private final InventoryRequestDTO requestDTO;
    private WorkFlowStepStatus stepStatus = WorkFlowStepStatus.PENDING;

    public InventoryStep(WebClient webClient, InventoryRequestDTO requestDTO) {
        this.webClient = webClient;
        this.requestDTO = requestDTO;
    }

    @Override
    public WorkFlowStepStatus getStatus() {
        return stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return webClient
                .post()
                .uri("/inventory/deduct")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(InventoryResponseDTO.class)
                .map(response -> response.getStatus().equals(InventoryStatus.AVAILABLE))
                .doOnNext(b -> stepStatus = b ? WorkFlowStepStatus.COMPLETE : WorkFlowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return webClient
                .post()
                .uri("/inventory/add")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }
}
