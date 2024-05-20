package io.matheusvictor.orderservice.service.impl;

import io.matheusvictor.orderservice.dto.OrchestratorResponseDTO;
import io.matheusvictor.orderservice.repository.PurchaseOrderRepository;
import io.matheusvictor.orderservice.service.UpdateService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateServiceImpl implements UpdateService {

    private final PurchaseOrderRepository repository;

    public UpdateServiceImpl(PurchaseOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Void> updateOrder(OrchestratorResponseDTO responseDTO) {
        return repository.findById(responseDTO.getOrderId())
                .doOnNext(purchaseOrder -> purchaseOrder.setStatus(responseDTO.getStatus()))
                .doOnNext(repository::save)
                .then();
    }
}
