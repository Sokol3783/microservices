package com.example.service1.order_service.integration.payments.client.feign;

import com.example.service1.order_service.integration.payments.dto.payment.PaymentRequestDTO;
import com.example.service1.order_service.integration.payments.dto.payment.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "payment-service", url = "http://localhost:8082/api/v1/payments")
public interface PaymentFeignClient {

  @PostMapping
  PaymentResponse processPayment(@RequestBody PaymentRequestDTO paymentRequestDTO,
                                @RequestHeader("X-Idempotency-Key") String idempotencyKey);

}
