package com.example.service1.order_service.integration.payments.dto.payment;

import com.example.service1.order_service.valueobjects.Customer;

public record PaymentRequestDTO(Long orderNumber, Double amount, Customer customer) {
}
