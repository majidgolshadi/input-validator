package com.golshadi.majid.inputvalidator;

import static org.assertj.core.api.Assertions.assertThat;

import com.golshadi.majid.inputvalidator.mock.AlwaysInvalidValidator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.ServletRequestDataBinder;

public class DataBinderLearningTests {

  private final static String KEY = "KEY";
  private final static String KEY_2 = "KEY_2";
  private final static String KEY_3 = "KEY_3";
  private final static String VALUE = "VALUE";
  private final static String VALUE_2 = "VALUE_2";

  public Map<String, Object> makeFlattenData() {
    Map<String, Object> flattenData = new HashMap<>();

    flattenData.put(KEY, VALUE);
    flattenData.put(KEY_2, VALUE_2);

    return flattenData;
  }

  @Test
  public void validateTest() {
    Map<String, Object> flattenData = makeFlattenData();
    DataBinder dataBinder = new DataBinder(flattenData);

    Validator[] validators = new Validator[]{new AlwaysInvalidValidator()};

    dataBinder.setRequiredFields(KEY, KEY_3);
    dataBinder.addValidators(validators);

    MutablePropertyValues propertyValues = new MutablePropertyValues();
    dataBinder.bind(propertyValues);
    dataBinder.validate();

    BindingResult bindingResult = dataBinder.getBindingResult();

    assertThat(bindingResult.getErrorCount()).isEqualTo(3);

    bindingResult.addError(new ObjectError(KEY_2, "Custom error"));

    assertThat(bindingResult.getErrorCount()).isEqualTo(4);
  }

  @Test
  public void servletRequestValidateTest() {
    Map<String, Object> flattenData = makeFlattenData();
    ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(flattenData);

    dataBinder.setRequiredFields(KEY, KEY_3);
    dataBinder.addValidators();

    dataBinder.bind(new MutablePropertyValues());
    dataBinder.validate();

    System.out.println("salam");
  }
}