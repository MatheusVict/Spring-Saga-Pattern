package io.matheusvictor.paymentservice.service.impl;

import io.matheusvictor.paymentservice.dto.PaymentRequestDTO;
import io.matheusvictor.paymentservice.dto.PaymentResponseDTO;
import io.matheusvictor.paymentservice.dto.PaymentStatus;
import io.matheusvictor.paymentservice.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Map<Integer, Double> payment;

    @PostConstruct
    private void init() {
        payment = new HashMap<>();
        payment.put(1, 780.0);
        payment.put(2, 1200.0);
        payment.put(3, 6461.5);
    }

    @Override
    public PaymentResponseDTO debit(PaymentRequestDTO paymentRequestDTO) {
        int userId = paymentRequestDTO.getUserId();

        double balance = payment.getOrDefault(userId, 0.0);

        PaymentResponseDTO paymentResponseDTO = new PaymentResponseDTO();

        paymentResponseDTO.setAmount(paymentResponseDTO.getAmount());
        paymentResponseDTO.setOrderId(paymentRequestDTO.getOrderId());
        paymentResponseDTO.setUserId(userId);

        if (balance >= paymentRequestDTO.getAmount()) {
            paymentResponseDTO.setStatus(PaymentStatus.APPROVED);

            double newBalance = balance - paymentRequestDTO.getAmount();
            payment.put(userId, newBalance);
        } else {
            paymentResponseDTO.setStatus(PaymentStatus.REJECTED);
        }
        return paymentResponseDTO;
    }

    @Override
    public void credit(PaymentRequestDTO paymentRequestDTO) {
        payment.computeIfPresent(paymentRequestDTO.getUserId(), (k, v) -> v + paymentRequestDTO.getAmount());
    }
}
