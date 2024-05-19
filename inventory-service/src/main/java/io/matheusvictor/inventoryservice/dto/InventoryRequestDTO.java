package io.matheusvictor.inventoryservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class InventoryRequestDTO {
    private Integer userId;
    private Integer productId;
    private UUID orderId;
}
