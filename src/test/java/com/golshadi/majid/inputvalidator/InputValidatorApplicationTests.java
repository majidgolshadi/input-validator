package com.golshadi.majid.inputvalidator;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.fge.jackson.JsonLoader;
import java.io.IOException;
import java.time.Instant;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class InputValidatorApplicationTests {

  private final static String RULE_KEY_1 = "v1/test/key";
  private final static String RULE_KEY_2 = "v2/test/key";
  private final static String VALID_JSON_DATA_PATH = "/valid.json";
  private final static String VALID_JSON_DATA_PATH_2 = "/valid-2.json";
  private final static String SINGLE_INVALID_FIELD_JSON_DATA_PATH = "/single-invalid-field.json";
  private final static String EXTRA_FIELD_JSON_DATA_PATH = "/extra-field-invalid.json";
  private final static String NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH = "/not-exists-required-field.json";
  private final static String COMBINATION_OF_ISSUES_JSON_DATA_PATH = "/combination-of-issues.json";

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  private String validationUrl;

  @BeforeEach
  public void setUp() {
    validationUrl = String.format("http://localhost:%d/validate", port);
  }

  @Test
  void badRequestRequiredFieldErrorTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(NOT_EXISTS_REQUIRED_FIELD_JSON_DATA_PATH);
    var request = makeRequestEntity(RULE_KEY_1, jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, ErrorResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    assertThat(response.getBody().getMessage()).isEqualTo("required fields latitude does not exists");
  }

  @Test
  void badRequestInvalidFieldErrorTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(SINGLE_INVALID_FIELD_JSON_DATA_PATH);
    var request = makeRequestEntity(RULE_KEY_1, jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, ErrorResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    assertThat(response.getBody().getMessage()).isEqualTo("invalid fields: .latitude");
  }

  @Test
  void badRequestExtraFieldErrorTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(EXTRA_FIELD_JSON_DATA_PATH);
    var request = makeRequestEntity("v1/test/key", jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, ErrorResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    assertThat(response.getBody().getMessage()).isEqualTo("extra fields exist: extra_field");
  }

  @Test
  void badRequestCombinationIssueErrorTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(COMBINATION_OF_ISSUES_JSON_DATA_PATH);
    var request = makeRequestEntity(RULE_KEY_1, jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, ErrorResponse.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    assertThat(response.getBody().getError()).isEqualTo("Bad Request");
    assertThat(response.getBody().getMessage()).isEqualTo("extra fields exist: extra");
  }

  @Test
  void validDataTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(VALID_JSON_DATA_PATH);
    var request = makeRequestEntity(RULE_KEY_1, jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void supportMultipleSchemaJsonConfigTest() throws IOException {
    var jsonNode = JsonLoader.fromResource(VALID_JSON_DATA_PATH_2);
    var request = makeRequestEntity(RULE_KEY_2, jsonNode.toString());
    var response = restTemplate.exchange(validationUrl, HttpMethod.POST, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  private HttpEntity<String> makeRequestEntity(String header, String body) {
    var headers = new HttpHeaders();

    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    headers.add("X-B3-URL", header);

    return new HttpEntity<>(body, headers);
  }

}

@Data
class ErrorResponse {

  private final String timestamp;
  private final int status;
  private final String error;
  private final String message;
}