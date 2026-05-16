package com.example.service2.payment_service.interceptor;

import com.example.service2.payment_service.entity.IdempotencyKey;
import com.example.service2.payment_service.service.IdempotencyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static com.example.service2.payment_service.filter.ResponseWrapperForIdempotencyFilterInterceptorFilter.WRAPPED_RESPONSE_ATTRIBUTE;

@RequiredArgsConstructor
@Component
public class IdempotencyInterceptor implements HandlerInterceptor {

  private static final String KEY_NAME = "X-Idempotency-key";
  private final IdempotencyService idempotencyService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
     var method = HttpMethod.valueOf(request.getMethod());
     if (method.equals(HttpMethod.POST)) {
       var xkey = request.getHeader(KEY_NAME);
       if (xkey.isEmpty()) {
          response.setStatus(HttpStatus.BAD_REQUEST.value());
          response.getWriter().printf("%s header is not present", KEY_NAME);
          return false;
       }
       return processIdempotency(xkey, response);
     }

     return true;
  }

  private boolean processIdempotency(String xIdempotencyKey, HttpServletResponse response) {
     var existingKey = idempotencyService.findByKey(xIdempotencyKey);
     return existingKey.map(idempotencyKey -> processPresentKey(idempotencyKey, response)).orElse(
             saveNewKey(xIdempotencyKey));
  }

    private boolean saveNewKey(String xIdempotencyKey) {
      idempotencyService.createPendingKey(xIdempotencyKey);
      return true;
    }

    @SneakyThrows
    private boolean processPresentKey(IdempotencyKey idempotencyKey, HttpServletResponse response) {
       var keyStatus = idempotencyKey.getStatus();
       if (keyStatus == IdempotencyKey.KeyStatus.PENDING) {
           response.setStatus(HttpStatus.CONFLICT.value());
           response.getWriter().println("Same status is already in progress");
       } else if (keyStatus == IdempotencyKey.KeyStatus.COMPLETE) {
         response.setStatus(idempotencyKey.getStatusKode());
         response.setContentType("application/json");
         response.getWriter().print(idempotencyKey.getResponseData());
       } else {
           throw new IllegalArgumentException("Invalid impotency key status");
       }
       return false;
    }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, @Nullable Exception ex) throws Exception {
      var method = HttpMethod.valueOf(request.getMethod());
      if (method.equals(HttpMethod.POST)) {
          var wrappedResponse = (ContentCachingResponseWrapper) request.getAttribute(WRAPPED_RESPONSE_ATTRIBUTE);
          var responseBody = new String(wrappedResponse.getContentAsByteArray(), wrappedResponse.getCharacterEncoding());

          var xkey = request.getHeader(KEY_NAME);
          idempotencyService.markKeyAsCompleted(xkey, response.getStatus(), responseBody);
      }

  }
}
