package com.jun.portalservice.domain.validators;

import com.jun.portalservice.domain.utils.Helper;
import com.jun.portalservice.domain.custom_annotations.PhoneNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

  @Override
  public void initialize(PhoneNumberConstraint phoneNumber) {}

  @Override
  public boolean isValid(String phoneNumberField, ConstraintValidatorContext context) {
    if (phoneNumberField == null || phoneNumberField.equals("") || phoneNumberField.length() < 10) {
      return true;
    }
    return Helper.regexPhoneNumber(phoneNumberField);
  }
}
