package io.matheusvictor.orderservice.controller;

import io.matheusvictor.orderservice.dto.OrderRequestDTO;
import io.matheusvictor.orderservice.dto.OrderResponseDTO;
import io.matheusvictor.orderservice.entity.PurchaseOrder;
import io.matheusvictor.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public Mono<PurchaseOrder> createOrder(@RequestBody Mono<OrderRequestDTO> body) {
        return body.flatMap(orderService::createOrder);
    }

    public Flux<OrderResponseDTO> getOrders() {
        return orderService.getAllOrder();
    }
}
