package com.golshadi.majid.inputvalidator.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.golshadi.majid.inputvalidator.mock.AlwaysInvalidValidator;
import com.golshadi.majid.inputvalidator.mock.AlwaysValidValidator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;

public class FlattenDataBinderHandlerTests {

  private final static String KEY_FIELD = "KEY_FIELD";
  private final static String KEY_FIELD_2 = "KEY_FIELD_2";

  @Test
  public void checkRequiredFieldsTest() {
    BindingResult bindingResult = new BindingResult();
    Map<String, List<Validator>> keyValidators = new HashMap<>();
    ValidatorProvider validatorProvider = new ValidatorProvider(keyValidators);

    Map<String, Object> data = new HashMap<String, Object>(){{
      put(KEY_FIELD, "VALUE");
    }};

    DataBinderHandler dataBinderHandler = new FlattenDataBinderHandler(validatorProvider, data);

    dataBinderHandler.setRequiredFields(KEY_FIELD);
    dataBinderHandler.bind();
    bindingResult = dataBinderHandler.getBindingResult();
    assertThat(bindingResult.getRequiredError().size()).isEqualTo(0);

    dataBinderHandler.setRequiredFields(KEY_FIELD, KEY_FIELD_2);
    dataBinderHandler.bind();
    bindingResult = dataBinderHandler.getBindingResult();

    assertThat(bindingResult.getRequiredError().size()).isEqualTo(1);
  }

  @Test
  public void passValidationTest() {
    BindingResult bindingResult = new BindingResult();
    Map<String, List<Validator>> keyValidators = new HashMap<>();
    keyValidators.put(KEY_FIELD, new ArrayList<Validator>(){{
      add(new AlwaysValidValidator());
    }});
    ValidatorProvider validatorProvider = new ValidatorProvider(keyValidators);

    Map<String, Object> data = new HashMap<String, Object>(){{
      put(KEY_FIELD, "VALUE");
    }};

    DataBinderHandler dataBinderHandler = new FlattenDataBinderHandler(validatorProvider, data);
    dataBinderHandler.validate();
    bindingResult = dataBinderHandler.getBindingResult();

    assertThat(bindingResult.getNotValidateFields().size()).isEqualTo(0);
    assertThat(bindingResult.getErrorCount()).isEqualTo(0);
  }

  @Test
  public void failedValidationTest() {
    BindingResult bindingResult = new BindingResult();
    Map<String, List<Validator>> keyValidators = new HashMap<>();
    keyValidators.put(KEY_FIELD, new ArrayList<Validator>(){{
      add(new AlwaysInvalidValidator());
    }});
    ValidatorProvider validatorProvider = new ValidatorProvider(keyValidators);

    Map<String, Object> data = new HashMap<String, Object>(){{
      put(KEY_FIELD, "VALUE");
    }};

    DataBinderHandler dataBinderHandler = new FlattenDataBinderHandler(validatorProvider, data);
    dataBinderHandler.validate();
    bindingResult = dataBinderHandler.getBindingResult();

    assertThat(bindingResult.getInvalidFields().size()).isEqualTo(1);
    assertThat(bindingResult.getErrorCount()).isEqualTo(1);
  }

  @Test
  public void noValidatorExistsForKeyValidationTest() {
    BindingResult bindingResult = new BindingResult();
    Map<String, List<Validator>> keyValidators = new HashMap<>();
    keyValidators.put(KEY_FIELD, new ArrayList<Validator>(){{
      add(new AlwaysInvalidValidator());
    }});
    ValidatorProvider validatorProvider = new ValidatorProvider(keyValidators);

    Map<String, Object> data = new HashMap<String, Object>(){{
      put(KEY_FIELD_2, "VALUE");
    }};

    DataBinderHandler dataBinderHandler = new FlattenDataBinderHandler(validatorProvider, data);
    dataBinderHandler.validate();
    bindingResult = dataBinderHandler.getBindingResult();

    assertThat(bindingResult.getNotValidateFields().size()).isEqualTo(1);
    assertThat(bindingResult.getErrorCount()).isEqualTo(1);
  }
}
