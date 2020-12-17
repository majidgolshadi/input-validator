package com.golshadi.majid.inputvalidator.component;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;

@Component
@ConfigurationPropertiesBinding
public class ClassValidatorConverter implements Converter<String, Class<Validator>> {

  @Override
  public Class<Validator> convert(String packageName) {
    try {
      return (Class<Validator>) Class.forName(packageName);

    } catch (ClassNotFoundException ignore) {
    }

    return null;
  }
}
