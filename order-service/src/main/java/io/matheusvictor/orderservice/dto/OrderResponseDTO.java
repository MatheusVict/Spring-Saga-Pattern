package io.matheusvictor.orderservice.dto;

import io.matheusvictor.orderservice.entity.PurchaseOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderResponseDTO {
	private UUID orderId;
	private Integer userId;
	private Integer productId;
	private Double amount;
	private OrderStatus status;

	public OrderResponseDTO entityToDTO(PurchaseOrder purchaseOrder) {
		System.out.println("Purchase order status: " + purchaseOrder.getStatus());
		this.orderId = purchaseOrder.getId();
		this.userId = purchaseOrder.getUserId();
		this.productId = purchaseOrder.getProductId();
		this.amount = purchaseOrder.getPrice();
		this.status = purchaseOrder.getStatus();
		return this;
	}
}