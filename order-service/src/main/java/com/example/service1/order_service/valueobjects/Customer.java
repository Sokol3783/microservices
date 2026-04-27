package com.example.service1.order_service.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // Для JPA
@AllArgsConstructor
public class Customer{

  @Column(name = "customer_name")
  private String name;

  @Column(name = "customer_email")
  private String email;

  @Column(name = "customer_address")
  private String address;

}
