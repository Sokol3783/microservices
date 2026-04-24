package com.example.service1.order_serivce.entity;

import com.example.service1.order_serivce.valueobjects.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "_orders")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
  @SequenceGenerator(name = "order_seq", allocationSize = 10)
  private Long id;

  @Column(name = "amount")

  private Long amount;

  @Embedded
  private Customer customer;

  @OneToMany(
      mappedBy = "order",
      cascade = CascadeType.ALL,
      orphanRemoval = true
  )
  @EqualsAndHashCode.Exclude
  private Set<OrderItem> orderItems;

  public void addOrderItem(OrderItem item) {
    orderItems.add(item);
    this.amount = orderItems.stream().mapToLong(OrderItem::getAmount).sum();
    item.setOrder(this);
  }

}
