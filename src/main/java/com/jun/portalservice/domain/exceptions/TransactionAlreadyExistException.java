package com.jun.portalservice.domain.exceptions;

public class TransactionAlreadyExistException extends RuntimeException {
  public TransactionAlreadyExistException(String exception) {
    super(exception);
  }
}
