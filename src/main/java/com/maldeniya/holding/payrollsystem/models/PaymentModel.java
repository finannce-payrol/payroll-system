package com.maldeniya.holding.payrollsystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaymentModel {
    private String userId;
    private PaymentType paymentType;
    private Double amount;
    private Currency currency;
    private String description;

}
