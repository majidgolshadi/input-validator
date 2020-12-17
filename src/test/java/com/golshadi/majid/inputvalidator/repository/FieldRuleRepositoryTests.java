package com.golshadi.majid.inputvalidator.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.golshadi.majid.inputvalidator.configuration.FieldValidator;
import com.golshadi.majid.inputvalidator.configuration.FlattenValidationConfiguration;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import com.golshadi.majid.inputvalidator.mock.AlwaysValidValidator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldRuleRepositoryTests {

  private final static String URL = "URL";
  private final static String REQUEST_METHOD = "URL";
  private final static String FIELD_KEY = "FIELD_KEY";
  private final static Class VALIDATOR_CLASS = AlwaysValidValidator.class;

  private String ruleKey;
  private FieldRuleRepository fieldRuleRepository;

  @BeforeEach
  public void setUp() {
    ruleKey = makeRuleKey(URL, REQUEST_METHOD);
    RequestValidationConfiguration configuration = makeRequestValidationConfiguration();
    fieldRuleRepository = new FieldRuleRepository(configuration);
    fieldRuleRepository.postConstruct();
  }

  private RequestValidationConfiguration makeRequestValidationConfiguration() {
    RequestValidationConfiguration configuration = new RequestValidationConfiguration();
    var ruleList = new ArrayList<FlattenValidationConfiguration>();

    var fvConfig1 = new FlattenValidationConfiguration();
    fvConfig1.setKey(ruleKey);
    fvConfig1.setFields(makeFieldValidatorList(FIELD_KEY, true, VALIDATOR_CLASS));

    ruleList.add(fvConfig1);
    configuration.setRules(ruleList);

    return configuration;
  }

  private String makeRuleKey(String url, String requestMethod) {
    return String.format("%s/%s", url, requestMethod);
  }

  private List<FieldValidator> makeFieldValidatorList(String key, boolean mandatory,
      Class validator) {
    var fieldValidatorList = new ArrayList<FieldValidator>();

    FieldValidator fieldValidator = new FieldValidator();
    fieldValidator.setKey(key);
    fieldValidator.setMandatory(mandatory);
    fieldValidator.setValidatorsClass(List.of(validator));

    fieldValidatorList.add(fieldValidator);

    return fieldValidatorList;
  }

  @Test
  public void getRulesTest() {
    List<FieldValidator> fieldValidatorList = fieldRuleRepository.getRules(ruleKey.toString());

    assertThat(fieldValidatorList.size()).isEqualTo(1);

    FieldValidator fieldValidator = fieldValidatorList.get(0);
    assertThat(fieldValidator.getKey()).isEqualTo(FIELD_KEY);
    assertThat(fieldValidator.getValidatorsClass().get(0)).isEqualTo(VALIDATOR_CLASS);
  }
}
