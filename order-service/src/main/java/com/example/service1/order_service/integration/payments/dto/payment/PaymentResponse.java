package com.example.service1.order_service.integration.payments.dto.payment;

import org.apache.logging.log4j.CloseableThreadContext.Instance;

public record PaymentResponse(Long paymentId, Long orderNumber, PaymentStatus status, Instance date) {
}
