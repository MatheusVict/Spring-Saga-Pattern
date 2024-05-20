package io.matheusvictor.orderservice.config;

import io.matheusvictor.orderservice.dto.OrchestratorRequestDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Configuration
public class Config {

    @Bean
    public Sinks.Many<OrchestratorRequestDTO> sink() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Flux<OrchestratorRequestDTO> flux() {
        return sink().asFlux();
    }
}
