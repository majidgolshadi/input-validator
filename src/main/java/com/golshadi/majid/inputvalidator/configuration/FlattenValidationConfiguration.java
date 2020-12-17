package com.golshadi.majid.inputvalidator.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@Data
public class FlattenValidationConfiguration {

  public String key;
  public String description;

  @NestedConfigurationProperty
  public List<FieldValidator> fields;
}
