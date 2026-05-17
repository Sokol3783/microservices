package com.example.service2.payment_service.dao;

import com.example.service2.payment_service.entity.IdempotencyKey;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyKey, String> {

  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<IdempotencyKey> findById(String key);
}
