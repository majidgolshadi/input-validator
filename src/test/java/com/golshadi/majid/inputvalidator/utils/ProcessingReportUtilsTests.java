package com.golshadi.majid.inputvalidator.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ProcessingReportUtilsTests {

  private final static String SAMPLE_SCHEMA_PATH = "/json-schema.json";
  private final static String VALID_JSON_DATA_PATH = "/valid.json";
  private final static String SINGLE_INVALID_FIELD_JSON_DATA_PATH = "/single-invalid-field.json";
  private final static String EXTRA_FIELD_JSON_DATA_PATH = "/extra-field-invalid.json";
  private final static String NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH = "/not-exists-required-field.json";
  private final static String COMBINATION_OF_ISSUES_JSON_DATA_PATH = "/combination-of-issues.json";

  @Test
  public void validateDataBindingResultTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(VALID_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);
    var bindingResult = ProcessingReportUtils.toBindingResult(processingReport);

    assertThat(processingReport.isSuccess()).isTrue();
    assertThat(bindingResult.getErrorCount()).isEqualTo(0);
  }

  @Test
  public void singleInvalidFieldBindingResultTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(SINGLE_INVALID_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);
    var bindingResult = ProcessingReportUtils.toBindingResult(processingReport);

    assertThat(processingReport.isSuccess()).isFalse();
    assertThat(bindingResult.getErrorCount()).isGreaterThan(0);
    assertThat(bindingResult.getInvalidFields()).isEqualTo(List.of(".latitude"));
  }

  @Test
  public void extraFieldBindingResultTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(EXTRA_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);
    var bindingResult = ProcessingReportUtils.toBindingResult(processingReport);

    assertThat(processingReport.isSuccess()).isFalse();
    assertThat(bindingResult.getErrorCount()).isGreaterThan(0);
    assertThat(bindingResult.getExtraFields()).isEqualTo(List.of("extra_field"));
  }

  @Test
  public void notExistRequiredBindingResultTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData);
    var bindingResult = ProcessingReportUtils.toBindingResult(processingReport);

    assertThat(processingReport.isSuccess()).isFalse();
    assertThat(bindingResult.getErrorCount()).isGreaterThan(0);
    assertThat(bindingResult.getRequiredError()).isEqualTo(List.of("latitude"));
  }

  @Test
  public void combinationErrorBindingResultTest() throws IOException, ProcessingException {
    var jsonNode = JsonLoader.fromResource(SAMPLE_SCHEMA_PATH);
    var jsonData = JsonLoader.fromResource(COMBINATION_OF_ISSUES_JSON_DATA_PATH);

    var validator = JsonSchemaFactory.byDefault().getValidator();
    var processingReport = validator.validate(jsonNode, jsonData, true);
    var bindingResult = ProcessingReportUtils.toBindingResult(processingReport);

    assertThat(processingReport.isSuccess()).isFalse();
    assertThat(bindingResult.getErrorCount()).isGreaterThan(0);
    assertThat(bindingResult.getExtraFields()).isEqualTo(List.of("extra"));
    assertThat(bindingResult.getInvalidFields()).isEqualTo(List.of(".latitude", ".longitude"));
  }
}
