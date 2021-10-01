package com.jun.portalservice.domain.custom_annotations;

import com.jun.portalservice.domain.validators.UsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
  String message() default "Sai định dạng tài khoản !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
