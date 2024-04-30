package com.example.animalsapi.exception;

public class InvalidSortParameterException extends RuntimeException{
  public InvalidSortParameterException(String message) {
    super(message);
  }
}
