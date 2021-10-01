package com.jun.portalservice.domain.exceptions;

import lombok.Data;

import java.util.Map;

@Data
public class ErrorObject {
  private final String message;
  private final Integer errorCode;
  private final Map<String, String> data;
}
