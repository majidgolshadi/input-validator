package com.golshadi.majid.inputvalidator.service;

import java.lang.reflect.InvocationTargetException;
import org.springframework.validation.Validator;

// TODO: Support argument for each validator like Max(15)
class ValidatorFactory {

  public Validator newInstance(Class<Validator> validatorClass)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    return validatorClass.getDeclaredConstructor().newInstance();
  }
}
