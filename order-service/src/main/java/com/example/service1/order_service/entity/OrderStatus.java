package com.example.service1.order_service.entity;

import com.example.service1.order_service.integration.payments.dto.payment.PaymentStatus;

public enum OrderStatus {

  PENDING_PAYMENT,
  PAYMENT_RECEIVED,
  SHIPPED,
  DELIVERED,
  CANCELLED;

  public static OrderStatus fromPaymentStatus(PaymentStatus paymentStatus) {

    return switch (paymentStatus) {
      case PAID -> PAYMENT_RECEIVED;
      case PENDING -> PENDING_PAYMENT;
      case REJECTED -> CANCELLED;
    };
  }

}
