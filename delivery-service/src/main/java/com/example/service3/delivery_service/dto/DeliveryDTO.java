package com.example.service3.delivery_service.dto;

import com.example.service3.delivery_service.valueobjects.Customer;
import jakarta.validation.constraints.NotNull;

public record DeliveryDTO(Long orderNumber,
                         @NotNull Customer customer,
                          String address) {

}
