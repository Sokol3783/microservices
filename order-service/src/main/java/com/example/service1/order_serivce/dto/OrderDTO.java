package com.example.service1.order_serivce.dto;

import com.example.service1.order_serivce.valueobjects.Customer;
import java.util.Set;

public record OrderDTO(Customer customer, Long amount, Set<OrderItemDTO> items) {

  public record OrderItemDTO(String productName, Integer price, Integer amount, Integer quantity){

  }

}
