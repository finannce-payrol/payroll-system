package com.maldeniya.holding.payrollsystem.services;

import com.maldeniya.holding.payrollsystem.entityies.Payment;
import com.maldeniya.holding.payrollsystem.models.PaymentModel;
import com.maldeniya.holding.payrollsystem.models.PaymentType;
import com.maldeniya.holding.payrollsystem.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
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
        return getBasic(employeeId).map(payment ->
            new PaymentModel(
                payment.getUserId(),
                PaymentType.BASIC,
                (payment.getBasic() + getPersonalSaleQuota(employeeId)) * 90/100,
                payment.getCurrency(),
                PaymentType.TAKE_HOME.toString()))
                .orElseThrow(()->
                        new RuntimeException("Some problem happens in connection"));
    }

    public PaymentModel getAndCalculateFullSalary(String employeeId){
        return getBasic(employeeId).map(payment ->
                new PaymentModel(
                        payment.getUserId(),
                        PaymentType.BASIC,
                        payment.getBasic() + getPersonalSaleQuota(employeeId),
                        payment.getCurrency(),
                        PaymentType.FULL_ALLOWANCE.toString())).orElseThrow(()-> new RuntimeException("Some problem happens in connection"));
    }

    public void editBasicSalary(String employeeId, PaymentModel paymentModel){

        Optional<Payment> basicOptional = getBasic(employeeId);
        Payment payment = null;
        if(basicOptional.isEmpty()){
            payment = new Payment();
        }else {
            payment = basicOptional.get();
        }
        payment.setUserId(employeeId);
        payment.setBasic(paymentModel.getAmount());
        payment.setCurrency(paymentModel.getCurrency());
        paymentRepository.save(payment);
    }

    private Double getPersonalSaleQuota(String userId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Double> response = restTemplate.exchange("http://localhost:8082/platform/users/"+userId+"/sales", HttpMethod.GET,requestEntity, Double.class);
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }else {
            throw new RuntimeException("personal sales quota not found");
        }
    }
}
