package com.golshadi.majid.inputvalidator;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class JsonSchemaValidatorLearningTests {

  private final static String SAMPLE_SCHEMA_PATH = "/jsonSchema.json";
  private final static String JSON_DATA_PATH = "/data.json";

  @Test
  public void loadJsonSchemaTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);

    assertThat(processingReport.isSuccess()).isTrue();
  }
}
