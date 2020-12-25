package com.golshadi.majid.inputvalidator.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import com.golshadi.majid.inputvalidator.configuration.ValidationConfiguration;
import com.golshadi.majid.inputvalidator.repository.ValidationSchemaRepository;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorResolverTests {

  private final static String RULE_KEY = "RULE_KEY";
  private final static String JSON_SCHEMA = "/json-schema.json";

  private ValidatorResolver validatorResolver;

  @BeforeEach
  public void setUp() throws IOException {
    var repository = makeRepository();
    validatorResolver = new ValidatorResolver(repository);
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
  public void getValidatorTest() throws ProcessingException {
    // load from repository
    var jsonSchema = validatorResolver.getValidator(RULE_KEY);
    assertThat(jsonSchema).isEqualTo(jsonSchema);

    // load from cache
    var jsonSchema2 = validatorResolver.getValidator(RULE_KEY);
    assertThat(jsonSchema2).isEqualTo(jsonSchema);
  }
}
