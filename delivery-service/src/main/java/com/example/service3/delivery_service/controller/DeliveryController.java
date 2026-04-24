package com.example.service3.delivery_service.controller;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

import com.example.service3.delivery_service.dto.DeliveryDTO;
import com.example.service3.delivery_service.entity.Delivery;
import com.example.service3.delivery_service.service.DeliveryService;
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
@RequestMapping("/api/v1/deliveries")
@Validated
@RequiredArgsConstructor
public class DeliveryController {

  @Autowired
  private final DeliveryService DeliveryService;

  @GetMapping
  public ResponseEntity<List<Delivery>>gatAllDelivery(){
    return ResponseEntity.ok(DeliveryService.findAll());
  }

  @PostMapping
  public ResponseEntity<Delivery> saveDelivery(@RequestBody @Valid DeliveryDTO DeliveryDTO) {
    var savedDelivery = DeliveryService.saveDelivery(DeliveryDTO);
    return ResponseEntity
        .created(fromCurrentRequest().path("/{id}").buildAndExpand(savedDelivery.getId()).toUri())
        .body(savedDelivery);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getDelivery(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id) {
    return DeliveryService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateDelivery(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id,
      @RequestBody @Valid DeliveryDTO DeliveryDTO){
      return DeliveryService.findById(id).map(
          Delivery -> ResponseEntity.status(200)
              .body(DeliveryService.updateDelivery(id, DeliveryDTO)))
          .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteDelivery(@PathVariable("id") @Positive(message = "Min value have to be 1") Long id){
    return DeliveryService.findById(id).map(presentDelivery -> {
      DeliveryService.deleteDelivery(presentDelivery);
      return ResponseEntity.status(204).build();
    }).orElse(ResponseEntity.notFound().build());
  }

}
