package com.golshadi.majid.inputvalidator.service;

import java.util.List;
import java.util.Map;
import org.springframework.validation.Validator;

class ValidatorProvider {

  private final Map<String, List<Validator>> fieldValidators;

  public ValidatorProvider(Map<String, List<Validator>> fieldValidators) {
    this.fieldValidators = fieldValidators;
  }

  public List<Validator> getValidators(String key) {
    return fieldValidators.get(key);
  }
}
