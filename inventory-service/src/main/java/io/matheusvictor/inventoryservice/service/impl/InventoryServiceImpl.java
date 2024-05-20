package io.matheusvictor.inventoryservice.service.impl;

import io.matheusvictor.inventoryservice.dto.InventoryRequestDTO;
import io.matheusvictor.inventoryservice.dto.InventoryResponseDTO;
import io.matheusvictor.inventoryservice.dto.InventoryStatus;
import io.matheusvictor.inventoryservice.service.InventoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {

    private Map<Integer, Integer> inventory;

    @PostConstruct
    private void init() {
        inventory = new HashMap<>();
        inventory.put(1, 2);
        inventory.put(2, 3);
        inventory.put(3, 4);
    }

    @Override
    public InventoryResponseDTO deduct(InventoryRequestDTO requestDTO) {
        int quantity = inventory.getOrDefault(requestDTO.getProductId(), 0);

        InventoryResponseDTO responseDTO = new InventoryResponseDTO();

        if (quantity == 0) {
            responseDTO.setStatus(InventoryStatus.UNAVAILABLE);
        } else {
            responseDTO.setStatus(InventoryStatus.AVAILABLE);
            inventory.put(requestDTO.getProductId(), quantity - 1);
        }

        responseDTO.setUserId(requestDTO.getUserId());
        responseDTO.setProductId(requestDTO.getProductId());
        responseDTO.setOrderId(requestDTO.getOrderId());

        return responseDTO;
    }

    @Override
    public void add(InventoryRequestDTO requestDTO) {
        inventory.computeIfPresent(requestDTO.getProductId(), (key, value) -> value + 1);
    }
}
