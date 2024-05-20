package io.matheusvictor.paymentservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PaymentResponseDTO {
    private Integer userId;
    private UUID orderId;
    private Double amount;
    private PaymentStatus status;
}
