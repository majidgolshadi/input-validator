package com.golshadi.majid.inputvalidator.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import com.golshadi.majid.inputvalidator.configuration.ValidationConfiguration;
import com.golshadi.majid.inputvalidator.repository.ValidationSchemaRepository;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTests {

  private final static String RULE_KEY = "RULE_KEY";
  private final static String VALID_JSON_DATA = "/valid.json";
  private final static String INVALID_JSON_DATA = "/combination-of-issues.json";
  private final static String JSON_SCHEMA = "/json-schema.json";

  private Validator validator;

  @BeforeEach
  public void setUp() throws IOException {
    var repository = makeRepository();

    var validatorResolver = new ValidatorResolver(repository);
    validator = new Validator(validatorResolver);
  }

  private ValidationSchemaRepository makeRepository() throws IOException {
    var validationConfiguration = makeValidationConfiguration(RULE_KEY, JSON_SCHEMA);
    var configuration = new RequestValidationConfiguration();
    configuration.setRules(List.of(validationConfiguration));

    var repository = new ValidationSchemaRepository(configuration);
    repository.postConstruct();

    return repository;
  }

  private ValidationConfiguration makeValidationConfiguration(String ruleKey, String jsonSchemaResource) {
    var validationConfiguration = new ValidationConfiguration();
    validationConfiguration.setKey(ruleKey);
    validationConfiguration.setJsonSchema(jsonSchemaResource);
    validationConfiguration.setDescription("some description");

    return validationConfiguration;
  }

  @Test
  public void validDataValidationTest() throws ProcessingException, IOException {
    var validJsonNode = JsonLoader.fromResource(VALID_JSON_DATA);
    var result = validator.validate(RULE_KEY, validJsonNode.toString());

    assertThat(result.getErrorCount()).isEqualTo(0);
  }

  @Test
  public void invalidDataValidationTest() throws ProcessingException, IOException {
    var validJsonNode = JsonLoader.fromResource(INVALID_JSON_DATA);
    var result = validator.validate(RULE_KEY, validJsonNode.toString());

    assertThat(result.getErrorCount()).isGreaterThan(0);
  }
}
