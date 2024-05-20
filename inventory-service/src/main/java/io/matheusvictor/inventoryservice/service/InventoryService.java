package io.matheusvictor.inventoryservice.service;

import io.matheusvictor.inventoryservice.dto.InventoryRequestDTO;
import io.matheusvictor.inventoryservice.dto.InventoryResponseDTO;
import org.springframework.stereotype.Component;

public interface InventoryService {
    InventoryResponseDTO deduct(InventoryRequestDTO requestDTO);
    void add(InventoryRequestDTO requestDTO);
}
