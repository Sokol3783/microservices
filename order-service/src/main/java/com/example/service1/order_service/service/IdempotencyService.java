package com.example.service1.order_service.service;

import com.example.service1.order_service.dao.IdempotencyRepository;
import com.example.service1.order_service.entity.IdempotencyKey;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

  private final IdempotencyRepository repository;

  public void createPendingKey(String key) {

  }

  public Optional<IdempotencyKey> findByKey(String key) {
    return repository.findById(key);
  }

  public void markKeyAsCompleted(String key) {

  }

}
