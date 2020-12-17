package com.golshadi.majid.inputvalidator.service;

import com.golshadi.majid.inputvalidator.configuration.FieldValidator;
import com.golshadi.majid.inputvalidator.repository.FieldRuleRepository;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

@Service
class ValidatorProviderResolver {

  private final FieldRuleRepository repository;
  private final ValidatorFactory validatorFactory;
  private final Map<String, ValidatorProvider> validatorProviders;

  public ValidatorProviderResolver(
      FieldRuleRepository repository) {
    this.repository = repository;

    this.validatorFactory = new ValidatorFactory();
    this.validatorProviders = new HashMap<>();
  }

  public ValidatorProvider getValidatorProvider(String ruleKey)
      throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
    var validatorProvider = validatorProviders.get(ruleKey);

    if (validatorProvider != null) {
      return validatorProvider;
    }

    var rules = repository.getRules(ruleKey);
    validatorProvider = newValidatorProvider(rules);
    validatorProviders.put(ruleKey, validatorProvider);

    return validatorProvider;
  }

  public ValidatorProvider newValidatorProvider(List<FieldValidator> fieldValidatorList)
      throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    var fieldValidators = new HashMap<String, List<Validator>>();

    for (FieldValidator rule : fieldValidatorList) {

      var validatorList = new ArrayList<Validator>();
      for (Class<Validator> clazz : rule.getValidatorsClass()) {
        validatorList.add(validatorFactory.newInstance(clazz));
      }

      fieldValidators.put(rule.getKey(), validatorList);
    }

    return new ValidatorProvider(fieldValidators);
  }
}