package com.example.animalsapi.web;

import com.example.animalsapi.exception.InvalidFileException;
import com.example.animalsapi.exception.InvalidSortParameterException;
import com.example.animalsapi.web.dto.ExceptionResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {
  @ExceptionHandler({InvalidFileException.class, InvalidSortParameterException.class})
  public ResponseEntity<ExceptionResponse> handleBadRequest(RuntimeException exception){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(exceptionResponse(exception.getMessage()));
  }

  private ExceptionResponse exceptionResponse(String message) {
    return new ExceptionResponse(message,
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")));
  }
}
