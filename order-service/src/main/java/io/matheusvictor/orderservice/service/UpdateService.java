package io.matheusvictor.orderservice.service;

import io.matheusvictor.orderservice.dto.OrchestratorResponseDTO;
import reactor.core.publisher.Mono;

public interface UpdateService {
    Mono<Void> updateOrder(OrchestratorResponseDTO orchestratorResponseDTO);
}
