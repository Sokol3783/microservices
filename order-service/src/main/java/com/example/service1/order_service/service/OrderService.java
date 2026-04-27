package com.example.service1.order_service.service;

import com.example.service1.order_service.dao.OrderRepository;
import com.example.service1.order_service.dto.OrderDTO;
import com.example.service1.order_service.dto.OrderDTO.OrderItemDTO;
import com.example.service1.order_service.entity.Order;
import com.example.service1.order_service.entity.OrderItem;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public Optional<Order> findById(Long id) {
    return orderRepository.findById(id);
  }

  public void deleteOrder(Order order) {
     orderRepository.delete(order);
  }

  public Order saveOrder(OrderDTO orderDTO) {
    var order = mapToOrder(orderDTO);
    return  orderRepository.save(order);
  }

  private Order mapToOrder(OrderDTO orderDTO) {
    var order = Order.builder().customer(orderDTO.customer()).orderItems(new HashSet<>()).build();
    for (OrderItemDTO itemDTO : orderDTO.items()) {
      order.addOrderItem(mapToItem(itemDTO));
    }
    return order;
  }

  private OrderItem mapToItem(OrderItemDTO itemDTO) {
    return OrderItem.builder()
        .productName(itemDTO.productName())
        .price(itemDTO.price())
        .amount(itemDTO.amount())
        .quantity(itemDTO.quantity()).
        build();
  }

  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  public Order updateOrder(Long id, OrderDTO orderDTO) {
    var order = mapToOrder(orderDTO);
    order.setId(id);
    return orderRepository.save(order);
  }

}
