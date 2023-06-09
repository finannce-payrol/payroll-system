package com.maldeniya.holding.payrollsystem.entityies;

import com.maldeniya.holding.payrollsystem.models.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Payment {
    @Id
    private String id;
    private String userId;
    private Double basic;
    private Currency currency;
}
