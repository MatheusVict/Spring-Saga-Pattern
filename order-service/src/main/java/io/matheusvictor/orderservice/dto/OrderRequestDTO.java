package io.matheusvictor.orderservice.dto;

import io.matheusvictor.orderservice.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequestDTO {
    private Integer userId;
    private Integer productId;
    private UUID orderId;

    public PurchaseOrder toEntity() {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(this.userId);
        purchaseOrder.setProductId(this.productId);
        purchaseOrder.setId(this.orderId);
        purchaseOrder.setStatus(OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }

    public OrchestratorRequestDTO getOrchestratorRequestDTO() {
        OrchestratorRequestDTO orchestratorRequestDTO = new OrchestratorRequestDTO();
        orchestratorRequestDTO.setUserId(this.getUserId());
        orchestratorRequestDTO.setProductId(this.getProductId());
        orchestratorRequestDTO.setOrderId(this.getOrderId());
        return orchestratorRequestDTO;
    }

}