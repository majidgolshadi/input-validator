package com.golshadi.majid.inputvalidator.service;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.golshadi.majid.inputvalidator.repository.ValidationSchemaRepository;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
class ValidatorResolver {

  private final ValidationSchemaRepository repository;
  private final JsonSchemaFactory validatorFactory;
  private final Map<String, JsonSchema> validatorProviders;

  public ValidatorResolver(
      ValidationSchemaRepository repository) {
    this.repository = repository;

    this.validatorProviders = new HashMap<>();
    this.validatorFactory = JsonSchemaFactory.byDefault();
  }

  public JsonSchema getValidator(String ruleKey) throws ProcessingException {
    var validator = validatorProviders.get(ruleKey);

    if (validator != null) {
      return validator;
    }

    var schema = repository.getSchema(ruleKey);
    validator = validatorFactory.getJsonSchema(schema);
    validatorProviders.put(ruleKey, validator);

    return validator;
  }
}