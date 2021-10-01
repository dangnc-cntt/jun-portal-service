package com.jun.portalservice.domain.exceptions;

public class NotEnoughException extends RuntimeException {
  public NotEnoughException(String exception) {
    super(exception);
  }
}
