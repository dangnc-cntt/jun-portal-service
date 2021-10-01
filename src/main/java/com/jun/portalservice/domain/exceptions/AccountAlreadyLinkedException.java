package com.jun.portalservice.domain.exceptions;

public class AccountAlreadyLinkedException extends BadRequestException {
  private static final long serialVersionUID = 1L;

  public AccountAlreadyLinkedException(String exception) {
    super(exception);
  }
}
