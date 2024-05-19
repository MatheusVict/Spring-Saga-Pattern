package io.matheusvictor.inventoryservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InventoryResponseDTO {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
    private InventoryStatus status;
}
