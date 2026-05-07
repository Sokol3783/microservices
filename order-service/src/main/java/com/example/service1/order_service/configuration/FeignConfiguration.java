package com.example.service1.order_service.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "com.example.service1.order_service.integration")
public class FeignConfiguration {

}
