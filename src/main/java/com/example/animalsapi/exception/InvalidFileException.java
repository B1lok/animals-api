package com.example.animalsapi.exception;

public class InvalidFileException extends RuntimeException{
  public InvalidFileException(String message) {
    super(message);
  }
}
