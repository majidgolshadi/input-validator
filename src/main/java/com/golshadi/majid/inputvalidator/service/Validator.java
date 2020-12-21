package com.golshadi.majid.inputvalidator.service;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.golshadi.majid.inputvalidator.utils.ProcessingReportUtils;
import java.io.IOException;
import org.springframework.stereotype.Service;

@Service
public class Validator {

  private final ValidatorResolver validatorResolver;

  public Validator(ValidatorResolver validatorResolver) {
    this.validatorResolver = validatorResolver;
  }

  public BindingResult validate(String ruleKey, String jsonData)
      throws ProcessingException, IOException {

    var jsonNode = JsonLoader.fromString(jsonData);
    var validator = validatorResolver.getValidator(ruleKey);

    return ProcessingReportUtils.toBindingResult(
        validator.validate(jsonNode)
    );
  }
}
