package com.maldeniya.holding.payrollsystem.controllers;

import com.maldeniya.holding.payrollsystem.models.PaymentModel;
import com.maldeniya.holding.payrollsystem.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/employee/{employeeId}")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(value = "/basic-payment")
    public ResponseEntity<PaymentModel> getAndCalculateBasicSalary(@PathVariable String employeeId){
        return ResponseEntity.ok(paymentService.getAndCalculateBasicSalary(employeeId));
    }

    @GetMapping(value = "/take-home-payment-requests")
    public ResponseEntity<PaymentModel> getAndCalculateTakeHome(@PathVariable String employeeId){
        return null;
    }

    @GetMapping(value = "/full-payment-requests")
    public ResponseEntity<PaymentModel> getAndCalculateFullSalary(@PathVariable String employeeId){
        return null;
    }

    @PutMapping(value = "/basic-payment")
    public ResponseEntity editBasicSalary(@PathVariable String employeeId, @RequestBody PaymentModel paymentModel){
        paymentService.editBasicSalary(employeeId, paymentModel);
        return ResponseEntity.noContent().build();
    }

}
