package io.matheusvictor.orderservice.service.impl;

import io.matheusvictor.orderservice.dto.OrchestratorRequestDTO;
import io.matheusvictor.orderservice.dto.OrchestratorResponseDTO;
import io.matheusvictor.orderservice.dto.OrderRequestDTO;
import io.matheusvictor.orderservice.dto.OrderResponseDTO;
import io.matheusvictor.orderservice.entity.PurchaseOrder;
import io.matheusvictor.orderservice.repository.PurchaseOrderRepository;
import io.matheusvictor.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Map<Integer, Double> ORDER_PRICE = Map.of(
            1, 100.0,
            2, 200.0,
            3, 300.0
    );

    private final PurchaseOrderRepository purchaseOrderRepository;


    private final Sinks.Many<OrchestratorRequestDTO> sink;

    public OrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository, Sinks.Many<OrchestratorRequestDTO> sink) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.sink = sink;
    }


    @Override
    public Mono<PurchaseOrder> createOrder(OrderRequestDTO orderRequestDTO) {
        return purchaseOrderRepository.save(orderRequestDTO.toEntity())
                .map(purchaseOrder -> {
                    purchaseOrder.setPrice(ORDER_PRICE.get(purchaseOrder.getProductId()));
                    return purchaseOrder;
                })
                .doOnNext(e -> orderRequestDTO.setOrderId(e.getId()))
                .doOnNext(e -> emitEvent(orderRequestDTO));
    }

    @Override
    public Flux<OrderResponseDTO> getAllOrder() {
        return purchaseOrderRepository.findAll()
                .map(this::entityToDTO);
    }

    private OrderResponseDTO entityToDTO(PurchaseOrder purchaseOrder) {
        return purchaseOrder.entityToDTO();
    }

    private void emitEvent(OrderRequestDTO requestDTO) {
        sink.tryEmitNext(getOrchestratorRequestDTO(requestDTO));
    }

    private OrchestratorRequestDTO getOrchestratorRequestDTO(OrderRequestDTO orderRequestDTO) {
        OrchestratorRequestDTO orchestratorRequestDTO = orderRequestDTO.getOrchestratorRequestDTO();
        orchestratorRequestDTO.setAmount(ORDER_PRICE.get(orderRequestDTO.getProductId()));
        return orchestratorRequestDTO;

    }
}
