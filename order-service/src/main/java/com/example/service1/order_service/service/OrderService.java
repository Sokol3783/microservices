package com.example.service1.order_service.service;



import com.example.service1.order_service.dao.OrderRepository;
import com.example.service1.order_service.dto.OrderDTO;
import com.example.service1.order_service.entity.MapperUtil;
import com.example.service1.order_service.entity.Order;
import com.example.service1.order_service.entity.OrderStatus;
import com.example.service1.order_service.integration.payments.client.feign.PaymentFeignClient;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final PaymentFeignClient paymentFeignClient;

  public Optional<Order> findById(Long id) {
    return orderRepository.findById(id);
  }

  public void deleteOrder(Order order) {
     orderRepository.delete(order);
  }

  public Order saveOrder(OrderDTO orderDTO) {
    var order = orderRepository.saveAndFlush(MapperUtil.mapToOrder(orderDTO));
    var payment = MapperUtil.mapToPaymentDTO(order);
    var paymentResponse = paymentFeignClient.processPayment(payment, payment.orderNumber().toString());
    order.setStatus(OrderStatus.fromPaymentStatus(paymentResponse.status()));
    orderRepository.save(order);
    return order;
  }

  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  public Order updateOrder(Long id, OrderDTO orderDTO) {
    var order = MapperUtil.mapToOrder(orderDTO);
    order.setId(id);
    return orderRepository.save(order);
  }

}
