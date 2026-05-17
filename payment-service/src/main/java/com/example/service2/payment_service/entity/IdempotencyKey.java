package com.example.service2.payment_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="idempotency_keys")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdempotencyKey {

  @Id
  @Column(name = "key_value")
  private String key;

  @Enumerated(EnumType.STRING)
  @Column(name = "key_status", nullable = false)
  private KeyStatus status;

  @Lob
  private String responseData;

  public enum KeyStatus {
    PENDING,
    COMPLETE
  }

  @Column(name = "status_code", nullable = false)
  private int StatusKode;

  public IdempotencyKey(String key) {
    this.key = key;
    this.status = KeyStatus.PENDING;
  }

}
