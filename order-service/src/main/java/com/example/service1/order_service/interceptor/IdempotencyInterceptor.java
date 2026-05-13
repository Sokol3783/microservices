package com.example.service1.order_service.interceptor;

import com.example.service1.order_service.entity.IdempotencyKey;
import com.example.service1.order_service.service.IdempotencyService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
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
       processIdempotency(xkey, response);
     }

     return true;
  }

  private void processIdempotency(String xkey, HttpServletResponse response) {
     var existingKey = idempotencyService.findByKey(xkey);


  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, @Nullable Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}
