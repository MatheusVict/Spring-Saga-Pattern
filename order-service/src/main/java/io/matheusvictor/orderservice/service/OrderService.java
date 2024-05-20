package io.matheusvictor.orderservice.service;

import io.matheusvictor.orderservice.dto.OrderRequestDTO;
import io.matheusvictor.orderservice.dto.OrderResponseDTO;
import io.matheusvictor.orderservice.entity.PurchaseOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Mono<PurchaseOrder> createOrder(OrderRequestDTO orderRequestDTO);
    Flux<OrderResponseDTO> getAllOrder();
}
