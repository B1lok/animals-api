package com.example.animalsapi.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse {
  private String message;
  private String timeStamp;
}
