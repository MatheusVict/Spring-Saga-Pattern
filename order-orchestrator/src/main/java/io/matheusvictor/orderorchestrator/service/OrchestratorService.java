package io.matheusvictor.orderorchestrator.service;

import io.matheusvictor.orderorchestrator.common.OrchestratorRequestDTO;
import io.matheusvictor.orderorchestrator.common.OrchestratorResponseDTO;
import reactor.core.publisher.Mono;

public interface OrchestratorService {
    Mono<OrchestratorResponseDTO> orderProduct(final OrchestratorRequestDTO requestDTO);
}
