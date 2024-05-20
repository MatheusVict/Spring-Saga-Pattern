package io.matheusvictor.paymentservice.controller;

import io.matheusvictor.paymentservice.dto.PaymentRequestDTO;
import io.matheusvictor.paymentservice.dto.PaymentResponseDTO;
import io.matheusvictor.paymentservice.service.PaymentService;
import io.matheusvictor.paymentservice.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {


    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/debit")
    public ResponseEntity<PaymentResponseDTO> debit(@RequestBody PaymentRequestDTO body) {
        return ResponseEntity.ok(paymentService.debit(body));
    }

    @PostMapping("/credit")
    public void credit(@RequestBody PaymentRequestDTO body) {
        paymentService.credit(body);
    }
}
