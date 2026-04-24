package com.example.service1.order_serivce.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.example.service1.order_serivce.dto.OrderDTO;
import com.example.service1.order_serivce.entity.Order;
import com.example.service1.order_serivce.service.OrderService;
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
  public ResponseEntity<List<Order>>gatAllOrder(){
    return ResponseEntity.ok(orderService.findAll());
  }

  @PostMapping
  public ResponseEntity<Order> saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
    var savedOrder = orderService.saveOrder(orderDTO);
    return ResponseEntity
        .created(fromCurrentRequest().path("/{id}").buildAndExpand(savedOrder.getId()).toUri())
        .body(savedOrder);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id) {
    return orderService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id,
      @RequestBody @Valid OrderDTO orderDTO){
      return orderService.findById(id).map(
          order -> ResponseEntity.status(200)
              .body(orderService.updateOrder(id, orderDTO)))
          .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteOrder(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id){
    return orderService.findById(id).map(presentOrder -> {
      orderService.deleteOrder(presentOrder);
      return ResponseEntity.status(204).build();
    }).orElse(ResponseEntity.notFound().build());
  }

}
