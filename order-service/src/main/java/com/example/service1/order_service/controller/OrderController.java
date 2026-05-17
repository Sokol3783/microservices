package com.example.service1.order_service.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.example.service1.order_service.dto.OrderDTO;
import com.example.service1.order_service.entity.Order;
import com.example.service1.order_service.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {

  @Autowired
  private final OrderService orderService;

  @GetMapping
  @CircuitBreaker(name = "readDeleteCircuitBreaker")
  public ResponseEntity<List<Order>>gatAllOrder(){
    return ResponseEntity.ok(orderService.findAll());
  }

  @PostMapping
  @CircuitBreaker(name = "stateChangingCircuitBreaker")
  public ResponseEntity<Order> saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
    var savedOrder = orderService.saveOrder(orderDTO);
    return ResponseEntity
        .created(fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId()).toUri())
        .body(savedOrder);
  }

  @GetMapping("/{id}")
  @CircuitBreaker(name = "readDeleteCircuitBreaker")
  public ResponseEntity<?> getOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id) {
    return orderService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  @CircuitBreaker(name = "stateChangingCircuitBreaker")
  public ResponseEntity<?> updateOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id,
      @RequestBody @Valid OrderDTO orderDTO){
      return orderService.findById(id).map(
          order -> ResponseEntity.status(200)
              .body(orderService.updateOrder(id, orderDTO)))
          .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @CircuitBreaker(name = "readDeleteCircuitBreaker")
  public ResponseEntity<?> deleteOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id){
    return orderService.findById(id).map(presentOrder -> {
      orderService.deleteOrder(presentOrder);
      return ResponseEntity.status(204).build();
    }).orElse(ResponseEntity.notFound().build());
  }

}
