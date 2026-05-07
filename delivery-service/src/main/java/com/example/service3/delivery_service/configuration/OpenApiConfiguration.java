package com.example.service3.delivery_service.configuration;

import io.swagger.v3.core.util.Yaml;
import io.swagger.v3.oas.models.OpenAPI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI customOpenAPI() throws IOException {

    ClassPathResource resource = new ClassPathResource("openapi.yaml");
    String openApiYaml = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
    return Yaml.mapper().readValue(openApiYaml, OpenAPI.class);

  }
}
