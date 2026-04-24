package com.example.service2.payment_service.service;

import com.example.service2.payment_service.dao.PaymentRepository;
import com.example.service2.payment_service.dto.PaymentDTO;
import com.example.service2.payment_service.entity.Payment;
import com.example.service2.payment_service.entity.PaymentStatus;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

  private final PaymentRepository PaymentRepository;

  public Optional<Payment> findById(Long id) {
    return PaymentRepository.findById(id);
  }

  public void deletePayment(Payment Payment) {
    PaymentRepository.delete(Payment);
  }

  public Payment savePayment(PaymentDTO PaymentDTO) {
    var Payment = mapToPayment(PaymentDTO);
    return PaymentRepository.save(Payment);
  }

  private Payment mapToPayment(PaymentDTO paymentDTO) {
    return Payment.builder().customer(paymentDTO.customer())
        .amount(paymentDTO.amount())
        .orderNumber(paymentDTO.orderNumber())
        .status(PaymentStatus.PENDING)
        .createdAT(Instant.now())
        .build();
  }

  public List<Payment> findAll() {
    return PaymentRepository.findAll();
  }

  public Payment updatePayment(Long id, PaymentDTO PaymentDTO) {
    var Payment = mapToPayment(PaymentDTO);
    Payment.setId(id);
    return PaymentRepository.save(Payment);
  }

}
