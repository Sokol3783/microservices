package com.example.service1.order_service.integration.payments.client.feign;

import com.example.service1.order_service.integration.payments.dto.payment.PaymentRequestDTO;
import com.example.service1.order_service.integration.payments.dto.payment.PaymentResponse;
import com.example.service1.order_service.integration.payments.dto.payment.PaymentStatus;
import feign.FeignException;
import java.nio.ByteBuffer;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
@RequiredArgsConstructor
public class PaymentClient implements PaymentFeignClient {

  private final PaymentFeignClient paymentClient;
  private final JsonMapper jsonMapper;

  public PaymentResponse processPayment(PaymentRequestDTO paymentRequestDTO) {
    try{
      return paymentClient.processPayment(paymentRequestDTO);
    } catch (FeignException ex) {
      return processException(ex);
    }
  }

  @SneakyThrows
  private PaymentResponse processException(FeignException ex) {
    var statusCode = HttpStatusCode.valueOf(ex.status());
    Optional<ByteBuffer> responseBody= ex.responseBody();

    if (isAcceptable(statusCode) && responseBody.isPresent()) {
        return responseBody.map(this::mapToPaymentResponse)
            .orElseThrow(() -> new RuntimeException("Response body is empty"));
    }

    return new PaymentResponse(null, null, PaymentStatus.PENDING, null);
  }

  private PaymentResponse mapToPaymentResponse(ByteBuffer body) {
    return jsonMapper.readValue(body.array(), PaymentResponse.class);
  }

  private boolean isAcceptable(HttpStatusCode statusCode) {
    return statusCode.is2xxSuccessful();
  }
}
