package com.example.service2.payment_service.entity;

import com.example.service2.payment_service.valueobjects.Customer;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "payments")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
  @SequenceGenerator(name = "payment_seq", allocationSize = 10)
  private Long id;

  @Column(name = "order_number")
  private Long orderNumber;

  @Embedded
  private Customer customer;

  @Column(name = "amount")
  private Long amount;

  @Column(name = "tax")
  private Long tax;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private PaymentStatus status;
  
  @Column(name = "created_at")
  private Instant createdAT;

}
