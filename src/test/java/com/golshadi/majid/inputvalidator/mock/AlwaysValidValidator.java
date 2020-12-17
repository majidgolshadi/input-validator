package com.golshadi.majid.inputvalidator.mock;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AlwaysValidValidator implements Validator {

  public AlwaysValidValidator() {
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return true;
  }

  @Override
  public void validate(Object target, Errors errors) {

  }
}
