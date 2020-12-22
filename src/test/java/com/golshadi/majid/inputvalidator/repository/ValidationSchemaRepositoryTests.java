package com.golshadi.majid.inputvalidator.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import com.golshadi.majid.inputvalidator.configuration.ValidationConfiguration;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidationSchemaRepositoryTests {

  private final static String RULE_KEY = "RULE_KEY";
  private final static String JSON_SCHEMA = "/json-schema.json";

  private ValidationSchemaRepository repository;
  private JsonNode jsonNode;

  @BeforeEach
  public void setUp() throws IOException {
    jsonNode = JsonLoader.fromResource(JSON_SCHEMA);
    repository = makeRepository();
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
  public void getSchemaTest() {
    var jsonSchema = repository.getSchema(RULE_KEY);

    assertThat(jsonSchema).isEqualTo(jsonNode);
  }
}
