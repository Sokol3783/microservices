package com.example.service3.delivery_service.service;

import com.example.service3.delivery_service.dao.DeliveryRepository;
import com.example.service3.delivery_service.dto.DeliveryDTO;
import com.example.service3.delivery_service.entity.Delivery;
import com.example.service3.delivery_service.entity.DeliveryStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final DeliveryRepository DeliveryRepository;

  public Optional<Delivery> findById(Long id) {
    return DeliveryRepository.findById(id);
  }

  public void deleteDelivery(Delivery Delivery) {
    DeliveryRepository.delete(Delivery);
  }

  public Delivery saveDelivery(DeliveryDTO DeliveryDTO) {
    var Delivery = mapToDelivery(DeliveryDTO);
    return DeliveryRepository.save(Delivery);
  }

  private Delivery mapToDelivery(DeliveryDTO deliveryDTO) {
    return Delivery.builder().customer(deliveryDTO.customer())
        .orderNumber(deliveryDTO.orderNumber())
        .status(DeliveryStatus.PACKING)
        .address(Optional.ofNullable(deliveryDTO.address()).orElse(deliveryDTO.customer()
            .getAddress()))
        .createdAT(Instant.now())
        .build();
  }

  public List<Delivery> findAll() {
    return DeliveryRepository.findAll();
  }

  public Delivery updateDelivery(Long id, DeliveryDTO DeliveryDTO) {
    var Delivery = mapToDelivery(DeliveryDTO);
    Delivery.setId(id);
    return DeliveryRepository.save(Delivery);
  }

}
