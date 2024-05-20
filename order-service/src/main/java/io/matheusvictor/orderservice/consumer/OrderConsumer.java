package io.matheusvictor.orderservice.consumer;

import io.matheusvictor.orderservice.dto.OrchestratorRequestDTO;
import io.matheusvictor.orderservice.dto.OrchestratorResponseDTO;
import io.matheusvictor.orderservice.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class OrderConsumer {

    @Autowired
    private Flux<OrchestratorRequestDTO> flux;

    @Autowired
    private UpdateService updateService;

    @Bean
    public Supplier<Flux<OrchestratorRequestDTO>> supplier() {
        return () -> flux;
    }

    @Bean
    public Consumer<Flux<OrchestratorResponseDTO>> consumer() {
        return c -> c.doOnNext(a -> System.out.println("Consuming: " + a))
                .flatMap(response -> updateService.updateOrder(response))
                .subscribe();
    }

}
