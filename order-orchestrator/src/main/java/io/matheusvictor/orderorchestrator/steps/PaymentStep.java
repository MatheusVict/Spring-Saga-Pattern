package io.matheusvictor.orderorchestrator.steps;

import io.matheusvictor.orderorchestrator.common.PaymentRequestDTO;
import io.matheusvictor.orderorchestrator.common.PaymentResponseDTO;
import io.matheusvictor.orderorchestrator.common.PaymentStatus;
import io.matheusvictor.orderorchestrator.service.WorkFlowStep;
import io.matheusvictor.orderorchestrator.service.WorkFlowStepStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PaymentStep implements WorkFlowStep {
    private final WebClient webClient;
    private final PaymentRequestDTO requestDTO;
    private WorkFlowStepStatus stepStatus = WorkFlowStepStatus.PENDING;

    public PaymentStep(WebClient webClient, PaymentRequestDTO requestDTO) {
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
                .uri("/payment/debit")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(PaymentResponseDTO.class)
                .map(response -> response.getStatus().equals(PaymentStatus.PAYMENT_APPROVED))
                .doOnNext(b -> stepStatus = b ? WorkFlowStepStatus.COMPLETE : WorkFlowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return webClient
                .post()
                .uri("/payment/credit")
                .body(BodyInserters.fromValue(requestDTO))
                .retrieve()
                .bodyToMono(Void.class)
                .map(response -> true)
                .onErrorReturn(false);
    }
}
