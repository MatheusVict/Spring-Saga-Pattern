package io.matheusvictor.orderservice.dto;

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


}