package com.jun.portalservice.domain.validators;

import com.jun.portalservice.domain.utils.Helper;
import com.jun.portalservice.domain.custom_annotations.DateConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class DateValidator implements ConstraintValidator<DateConstraint, Date> {

  @Override
  public void initialize(DateConstraint date) {}

  @Override
  public boolean isValid(Date dateField, ConstraintValidatorContext context) {
    if (dateField == null) {
      return true;
    }
    return Helper.compareDate(dateField);
  }
}
