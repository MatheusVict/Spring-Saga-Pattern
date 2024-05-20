package io.matheusvictor.orderservice.entity;

import io.matheusvictor.orderservice.dto.OrderResponseDTO;
import io.matheusvictor.orderservice.dto.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Getter
@Setter
public class PurchaseOrder {
    @Id
    private UUID id;
    private Integer userId;
    private Integer productId;
    private Double price;
    private OrderStatus status;

    public OrderResponseDTO entityToDTO() {
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(this.getId());
        orderResponseDTO.setUserId(this.getUserId());
        orderResponseDTO.setProductId(this.getProductId());
        orderResponseDTO.setAmount(this.getPrice());
        orderResponseDTO.setStatus(this.getStatus());
        return orderResponseDTO;
    }

}