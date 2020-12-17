package com.golshadi.majid.inputvalidator.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class IpValidator implements Validator {

  @Override
  public boolean supports(Class<?> aClass) {
    return String.class.equals(aClass);
  }

  @Override
  public void validate(Object o, Errors errors) {
    String ip = o.toString();

    if (ip.length() < 1) {
      errors.rejectValue("ip", "empty ip");
    }
  }
}
