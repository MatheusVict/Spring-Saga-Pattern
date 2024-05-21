package io.matheusvictor.orderorchestrator.common;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrchestratorRequestDTO {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private Double amount;

    public PaymentRequestDTO getPaymentRequestDTO() {
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setUserId(this.userId);
        paymentRequestDTO.setAmount(this.amount);
        paymentRequestDTO.setOrderId(this.orderId);
        return paymentRequestDTO;
    }

    public InventoryRequestDTO getInventoryRequestDTO() {

        InventoryRequestDTO inventoryRequestDTO = new InventoryRequestDTO();
        inventoryRequestDTO.setProductId(this.productId);
        inventoryRequestDTO.setOrderId(this.orderId);
        inventoryRequestDTO.setUserId(this.userId);
        return inventoryRequestDTO;
    }
}