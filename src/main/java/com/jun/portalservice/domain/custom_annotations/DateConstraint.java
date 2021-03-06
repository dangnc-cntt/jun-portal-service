package com.jun.portalservice.domain.custom_annotations;

import com.jun.portalservice.domain.validators.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateConstraint {
  String message() default "Ngày sinh lớn hơn ngày hiện tại !";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
