package com.golshadi.majid.inputvalidator.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.golshadi.majid.inputvalidator.mock.AlwaysValidValidator;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;
import org.springframework.validation.Validator;

public class ValidationFactoryTests {

  private final ValidatorFactory validatorFactory = new ValidatorFactory();

  @Test
  public void createInstanceTest()
      throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, ClassNotFoundException {
    String className = AlwaysValidValidator.class.getName();

    Class<Validator> clazz = (Class<Validator>) Class.forName(className);

    Validator validator = validatorFactory.newInstance(clazz);
    assertThat(validator).isNotNull();
  }
}
