package com.golshadi.majid.inputvalidator.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.golshadi.majid.inputvalidator.mock.AlwaysInvalidValidator;
import com.golshadi.majid.inputvalidator.mock.AlwaysValidValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;

public class ValidatorProviderTests {

  private final String FIELD_KEY = "FIELD_KEY";
  private ValidatorProvider validatorProvider;

  @BeforeEach
  public void setUp() {
    Map<String, List<Validator>> filedValidator = makeFieldValidators();
    validatorProvider = new ValidatorProvider(filedValidator);
  }

  private Map<String, List<Validator>> makeFieldValidators() {
    return new HashMap<String, List<Validator>>() {{
      put(FIELD_KEY, new ArrayList<Validator>() {{
        add(new AlwaysValidValidator());
        add(new AlwaysInvalidValidator());
      }});
    }};
  }

  @Test
  public void getValidatorsTest() {
    List<Validator> validatorList = validatorProvider.getValidators(FIELD_KEY);

    assertThat(validatorList.size()).isEqualTo(2);
  }

  @Test
  public void getNotExistValidatorsTest() {
    List<Validator> validatorList = validatorProvider.getValidators("NOT_EXIST_FIELD");

    assertThat(validatorList).isNull();
  }

}
