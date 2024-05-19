package io.matheusvictor.inventoryservice.controller;

import io.matheusvictor.inventoryservice.dto.InventoryRequestDTO;
import io.matheusvictor.inventoryservice.dto.InventoryResponseDTO;
import io.matheusvictor.inventoryservice.service.InventoryService;
import io.matheusvictor.inventoryservice.service.impl.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/deduct")
    public ResponseEntity<InventoryResponseDTO> deduct(@RequestBody InventoryRequestDTO body) {
        InventoryResponseDTO response = inventoryService.deduct(body);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public void add(@RequestBody InventoryRequestDTO body) {
        inventoryService.add(body);
    }
}
