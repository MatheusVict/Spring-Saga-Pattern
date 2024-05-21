package io.matheusvictor.orderorchestrator.config;

import io.matheusvictor.orderorchestrator.common.OrchestratorRequestDTO;
import io.matheusvictor.orderorchestrator.common.OrchestratorResponseDTO;
import io.matheusvictor.orderorchestrator.service.OrchestratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class OrchestratorConfig {
    @Autowired
    private OrchestratorService orchestratorService;

    @Bean
    public Function<Flux<OrchestratorRequestDTO>, Flux<OrchestratorResponseDTO>> processor() {
        return flux -> flux.flatMap(orchestratorService::orderProduct)
                .doOnNext(dto -> System.out.println("Status: " + dto.getStatus() + " - Order ID: " + dto.getOrderId()));
    }
}
