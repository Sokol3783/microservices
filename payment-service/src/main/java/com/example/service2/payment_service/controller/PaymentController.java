package com.example.service2.payment_service.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.example.service2.payment_service.dto.PaymentDTO;
import com.example.service2.payment_service.entity.Payment;
import com.example.service2.payment_service.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@Validated
@RequiredArgsConstructor
public class PaymentController {

  @Autowired
  private final PaymentService paymentService;

  @GetMapping
  public ResponseEntity<List<Payment>>gatAllPayment(){
    return ResponseEntity.ok(paymentService.findAll());
  }

  @PostMapping
  public ResponseEntity<Payment> savePayment(@RequestBody @Valid PaymentDTO paymentDTO) {
    var savedPayment = paymentService.savePayment(paymentDTO);
    return ResponseEntity
        .created(fromCurrentRequest().path("/{id}").buildAndExpand(savedPayment.getId()).toUri())
        .body(savedPayment);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPayment(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id) {
    return paymentService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePayment(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id,
      @RequestBody @Valid PaymentDTO PaymentDTO){
      return paymentService.findById(id).map(
          Payment -> ResponseEntity.status(200)
              .body(paymentService.updatePayment(id, PaymentDTO)))
          .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePayment(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id){
    return paymentService.findById(id).map(presentPayment -> {
      paymentService.deletePayment(presentPayment);
      return ResponseEntity.status(204).build();
    }).orElse(ResponseEntity.notFound().build());
  }

}
