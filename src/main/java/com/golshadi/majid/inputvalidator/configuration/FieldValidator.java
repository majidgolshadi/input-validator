package com.golshadi.majid.inputvalidator.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.Validator;

@Data
public class FieldValidator {

  private String key;
  private boolean mandatory = false;
  private List<Class<Validator>> validatorsClass;
}
