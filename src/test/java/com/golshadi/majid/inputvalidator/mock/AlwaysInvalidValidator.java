package com.golshadi.majid.inputvalidator.mock;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AlwaysInvalidValidator implements Validator {

  public AlwaysInvalidValidator() {
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public void validate(Object target, Errors errors) {
    errors.reject("always reject");
  }
}
