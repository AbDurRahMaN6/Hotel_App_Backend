package com.example.demo.controller;

import com.example.demo.models.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;
//    @PostMapping
//    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
//        Payment savedPayment = paymentRepository.save(payment);
//        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment);
//    }

    @PostMapping("/payment/create")
    public ResponseEntity<String> createPayment(@RequestBody Payment payment) {
        paymentRepository.save(payment);
        return ResponseEntity.ok("Payment created successfully.");
    }
}
