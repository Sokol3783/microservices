package com.example.service1.order_service.entity;

import com.example.service1.order_service.dto.OrderDTO;
import com.example.service1.order_service.dto.OrderDTO.OrderItemDTO;
import com.example.service1.order_service.integration.payments.dto.payment.PaymentRequestDTO;
import java.util.HashSet;

public class MapperUtil {

  public static PaymentRequestDTO mapToPaymentDTO(Order savedOrder) {
    return new PaymentRequestDTO(savedOrder.getId(), savedOrder.getOrderItems().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum(), savedOrder.getCustomer());
  }

  public static Order mapToOrder(OrderDTO orderDTO) {
    var order = Order.builder().customer(orderDTO.customer()).orderItems(new HashSet<>()).status(OrderStatus.PENDING_PAYMENT).build();
    for (OrderItemDTO itemDTO : orderDTO.items()) {
      order.addOrderItem(mapToItem(itemDTO));
    }
    return order;
  }

  private static OrderItem mapToItem(OrderItemDTO itemDTO) {
    return OrderItem.builder()
        .productName(itemDTO.productName())
        .price(itemDTO.price())
        .amount(itemDTO.amount())
        .quantity(itemDTO.quantity()).
        build();
  }

}
