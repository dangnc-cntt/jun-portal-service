package com.jun.portalservice.domain.validators;

import com.jun.portalservice.domain.config.BlackListEmail;
import com.jun.portalservice.domain.utils.Helper;
import com.jun.portalservice.domain.custom_annotations.UsernameConstraint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Autowired private BlackListEmail blackListEmail;

  @Override
  public void initialize(UsernameConstraint username) {}

  @Override
  public boolean isValid(String usernameField, ConstraintValidatorContext context) {
    if (usernameField == null || usernameField.equals("") || usernameField.length() < 6) {
      return true;
    }

    String afterLast = StringUtils.substringAfterLast(usernameField, "@");
    if (!Helper.regexPhoneNumber(usernameField)) {
      if (blackListEmail.getBlackList().size() > 0) {
        return Helper.regexEmail(usernameField)
            && !blackListEmail.getBlackList().contains(afterLast);
      } else {
        return Helper.regexEmail(usernameField);
      }
    } else {
      return Helper.regexPhoneNumber(usernameField);
    }
  }
}
