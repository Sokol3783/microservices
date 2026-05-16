package com.example.service2.payment_service.configuration;

import com.example.service2.payment_service.interceptor.IdempotencyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

  private final IdempotencyInterceptor interceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptor);
  }

}
