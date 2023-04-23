package com.maldeniya.holding.payrollsystem.services;

import com.maldeniya.holding.payrollsystem.entityies.Payment;
import com.maldeniya.holding.payrollsystem.models.PaymentModel;
import com.maldeniya.holding.payrollsystem.models.PaymentType;
import com.maldeniya.holding.payrollsystem.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    private Optional<Payment> getBasic(String userId){
         return paymentRepository.findByUserId(userId);

    }
    public PaymentModel getAndCalculateBasicSalary(String employeeId){
        return getBasic(employeeId)
                .map(payment -> new PaymentModel(
                        payment.getUserId(),
                        PaymentType.BASIC,
                        payment.getBasic(),
                        payment.getCurrency(),
                        PaymentType.BASIC.name()))
                .orElseThrow(()-> new RuntimeException("entity not found"));
    }

    public PaymentModel getAndCalculateTakeHome(String employeeId){
        return null;
    }

    public PaymentModel getAndCalculateFullSalary(String employeeId){
        return null;
    }

    public void editBasicSalary(String employeeId, PaymentModel paymentModel){
        getBasic(employeeId).ifPresentOrElse(payment-> {
            payment.setBasic(paymentModel.getAmount());
            payment.setCurrency(paymentModel.getCurrency());
            paymentRepository.save(payment);
        }, ()-> new RuntimeException("entity not found"));
    }
}
