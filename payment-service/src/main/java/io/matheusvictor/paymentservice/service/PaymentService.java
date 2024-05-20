package io.matheusvictor.paymentservice.service;

import io.matheusvictor.paymentservice.dto.PaymentRequestDTO;
import io.matheusvictor.paymentservice.dto.PaymentResponseDTO;
import org.springframework.stereotype.Component;


public interface PaymentService {
    PaymentResponseDTO debit(PaymentRequestDTO paymentRequestDTO);
    void credit(PaymentRequestDTO paymentRequestDTO);
}
