package com.golshadi.majid.inputvalidator;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class JsonSchemaValidatorLearningTests {

  private final static String SAMPLE_SCHEMA_PATH = "/json-schema.json";
  private final static String VALID_JSON_DATA_PATH = "/valid.json";
  private final static String SINGLE_INVALID_FIELD_JSON_DATA_PATH = "/single-invalid-field.json";
  private final static String EXTRA_FIELD_JSON_DATA_PATH = "/extra-field-invalid.json";
  private final static String NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH = "/not-exists-required-field.json";
  private final static String COMBINATION_OF_ISSUES_JSON_DATA_PATH = "/combination-of-issues.json";

  @Test
  public void validateDataTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(VALID_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);

    assertThat(processingReport.isSuccess()).isTrue();
  }

  @Test
  public void singleInvalidFieldTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(SINGLE_INVALID_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);

    /**
     * "error"
     * "/latitude"
     */
    processingReport.forEach(data -> {
      var map = data.asJson();
      System.out.println(map.get("level"));
      System.out.println(map.get("instance").get("pointer"));
    });
    assertThat(processingReport.isSuccess()).isFalse();
  }

  @Test
  public void extraFieldTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(EXTRA_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);

    /**
     * "error"
     * ["extra_field"]
     */
    processingReport.forEach(data -> {
      var map = data.asJson();
      System.out.println(map.get("level"));
      System.out.println(map.get("unwanted"));
    });
    assertThat(processingReport.isSuccess()).isFalse();
  }

  @Test
  public void notExistRequiredTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);

    /**
     * "error"
     * ["latitude"]
     */
    processingReport.forEach(data -> {
      var map = data.asJson();
      System.out.println(map.get("level"));
      System.out.println(map.get("missing"));
    });
    assertThat(processingReport.isSuccess()).isFalse();
  }

  @Test
  public void combinationErrorTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(COMBINATION_OF_ISSUES_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData, true);

    processingReport.forEach(data -> {
      var map = data.asJson();
      System.out.println(map);
    });
    assertThat(processingReport.isSuccess()).isFalse();
  }
}