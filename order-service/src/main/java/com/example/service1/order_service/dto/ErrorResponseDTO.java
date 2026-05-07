package com.example.service1.order_service.dto;

import java.util.List;

public record ErrorResponseDTO(String date, String errorMessage, List<ErrorField> errors) {

  public record ErrorField(String field, String invalidValue, String error) {
  }

}
