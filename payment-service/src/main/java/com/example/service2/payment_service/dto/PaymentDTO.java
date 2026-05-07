package com.example.service2.payment_service.dto;

import com.example.service2.payment_service.valueobjects.Customer;

public record PaymentDTO(Long orderNumber,
                         Long amount,
                         Customer customer) {
}
