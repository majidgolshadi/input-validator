package com.golshadi.majid.inputvalidator.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class ValidationSchemaRepository {

  private final RequestValidationConfiguration configuration;
  private final Map<String, JsonNode> datasource;

  public ValidationSchemaRepository(RequestValidationConfiguration configuration) {
    this.configuration = configuration;

    datasource = new HashMap<>();
  }

  @PostConstruct
  public void postConstruct() throws IOException {
    convertRequestValidationConfigurationToFiledRule();
  }

  private void convertRequestValidationConfigurationToFiledRule() throws IOException {
    for (var rule : configuration.getRules()) {
      datasource.put(
          rule.getKey(),
          JsonLoader.fromResource(rule.getJsonSchema())
      );
    }
  }

  public JsonNode getSchema(String ruleKey) {
    return datasource.get(ruleKey);
  }
}
