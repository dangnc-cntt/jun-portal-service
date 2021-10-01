package com.jun.portalservice.domain.validators;

import com.jun.portalservice.domain.utils.Helper;
import com.jun.portalservice.domain.custom_annotations.UrlConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<UrlConstraint, String> {
  @Override
  public void initialize(UrlConstraint url) {}

  @Override
  public boolean isValid(String urlField, ConstraintValidatorContext context) {
    if (urlField == null || urlField.equals("")) {
      return true;
    }
    return Helper.regexUrl(urlField);
  }
}
