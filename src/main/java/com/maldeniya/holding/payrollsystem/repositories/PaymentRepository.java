package com.maldeniya.holding.payrollsystem.repositories;

import com.maldeniya.holding.payrollsystem.entityies.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Optional<Payment> findByUserId(String userId);
}
