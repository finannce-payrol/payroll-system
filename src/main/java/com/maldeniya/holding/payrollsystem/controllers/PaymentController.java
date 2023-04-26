package com.maldeniya.holding.payrollsystem.controllers;

import com.maldeniya.holding.payrollsystem.models.PaymentModel;
import com.maldeniya.holding.payrollsystem.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users/{userId}")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(value = "/basic-payment")
    public ResponseEntity<PaymentModel> getAndCalculateBasicSalary(@PathVariable String userId){
        return ResponseEntity.ok(paymentService.getAndCalculateBasicSalary(userId));
    }

    @GetMapping(value = "/take-home-payment-requests")
    public ResponseEntity<PaymentModel> getAndCalculateTakeHome(@PathVariable String userId){
        return ResponseEntity.ok(paymentService.getAndCalculateTakeHome(userId));
    }

    @GetMapping(value = "/full-payment-requests")
    public ResponseEntity<PaymentModel> getAndCalculateFullSalary(@PathVariable String userId){
        return ResponseEntity.ok(paymentService.getAndCalculateFullSalary(userId));
    }

    @PostMapping(value = "/basic-payment")
    public ResponseEntity editBasicSalary(@PathVariable String userId, @RequestBody PaymentModel paymentModel){
        paymentService.editBasicSalary(userId, paymentModel);
        return ResponseEntity.noContent().build();
    }

}
