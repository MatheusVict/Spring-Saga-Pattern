package io.matheusvictor.orderorchestrator.common;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InventoryResponseDTO {
    private UUID orderId;
    private Integer userId;
    private Integer productId;
    private InventoryStatus status;
}