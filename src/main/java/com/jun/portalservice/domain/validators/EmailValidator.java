package com.jun.portalservice.domain.validators;

import com.jun.portalservice.domain.config.BlackListEmail;
import com.jun.portalservice.domain.utils.Helper;
import com.jun.portalservice.domain.custom_annotations.EmailConstraint;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

  @Autowired private BlackListEmail blackListEmail;

  @Override
  public void initialize(EmailConstraint email) {}

  @Override
  public boolean isValid(String emailField, ConstraintValidatorContext context) {
    if (emailField == null || emailField.equals("")) {
      return true;
    }
    String afterString = StringUtils.substringAfterLast(emailField, "@");
    if (blackListEmail.getBlackList().size() > 0) {
      return Helper.regexEmail(emailField) && !blackListEmail.getBlackList().contains(afterString);
    } else {
      return Helper.regexEmail(emailField);
    }
  }
}
