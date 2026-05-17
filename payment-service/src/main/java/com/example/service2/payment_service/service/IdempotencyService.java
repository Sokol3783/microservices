package com.example.service2.payment_service.service;

import java.util.Optional;

import com.example.service2.payment_service.dao.IdempotencyRepository;
import com.example.service2.payment_service.entity.IdempotencyKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

  private final IdempotencyRepository repository;

  public void createPendingKey(String key) {
    var idempotencyKey = new IdempotencyKey(key);
    repository.save(idempotencyKey);
  }

  public Optional<IdempotencyKey> findByKey(String key) {
    return repository.findById(key);
  }

  public void markKeyAsCompleted(String key, int statusCode, String responseBody ) {
    repository.findById(key)
            .map(idempotencyKey -> {
              idempotencyKey.setStatusKode(statusCode);
              idempotencyKey.setResponseData(responseBody);
              idempotencyKey.setStatus(IdempotencyKey.KeyStatus.COMPLETE);
              return repository.saveAndFlush(idempotencyKey);
            }
            ).orElseThrow();
 }
}
