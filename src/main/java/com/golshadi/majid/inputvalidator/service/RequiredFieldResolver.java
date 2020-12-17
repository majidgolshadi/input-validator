package com.golshadi.majid.inputvalidator.service;

import com.golshadi.majid.inputvalidator.configuration.FieldValidator;
import com.golshadi.majid.inputvalidator.repository.FieldRuleRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
class RequiredFieldResolver {

  private final FieldRuleRepository repository;
  private final Map<String, String[]> requestRequireFields;

  public RequiredFieldResolver(FieldRuleRepository repository) {
    this.repository = repository;

    this.requestRequireFields = new HashMap<>();
  }

  public String[] getRequiredFieldProvider(String ruleKey) {
    var requireFields = requestRequireFields.get(ruleKey);

    if (requireFields != null) {
      return requireFields;
    }

    var rules = repository.getRules(ruleKey);
    requireFields = extractRequiredFields(rules);
    requestRequireFields.put(ruleKey, requireFields);

    return requireFields;
  }

  public String[] extractRequiredFields(List<FieldValidator> fieldValidatorList) {
    var requiredFields = new ArrayList<String>();

    for (FieldValidator rule : fieldValidatorList) {
      if (rule.isMandatory()) {
        requiredFields.add(rule.getKey());
      }
    }

    return requiredFields.toArray(new String[0]);
  }
}