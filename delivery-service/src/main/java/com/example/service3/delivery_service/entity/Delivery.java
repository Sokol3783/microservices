package com.example.service3.delivery_service.entity;

import com.example.service3.delivery_service.valueobjects.Customer;
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
@Table(name = "delivery")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Delivery {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Delivery_seq")
  @SequenceGenerator(name = "Delivery_seq", allocationSize = 10)
  private Long id;

  @Column(name = "order_number")
  private Long orderNumber;

  @Embedded
  private Customer customer;

  @Column(name = "shipping_cost")
  private Long amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private DeliveryStatus status;

  @Column(name = "created_at")
  private Instant createdAT;

  private String address;

}
