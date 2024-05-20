package io.matheusvictor.orderservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrchestratorResponseDTO {

	private Integer userId;
	private Integer productId;
	private UUID orderId;
	private Double amount;
	private OrderStatus status;

}