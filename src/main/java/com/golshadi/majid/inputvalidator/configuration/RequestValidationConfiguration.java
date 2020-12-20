package com.golshadi.majid.inputvalidator.configuration;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "request-validation")
public class RequestValidationConfiguration {

  @NestedConfigurationProperty
  public List<ValidationConfiguration> rules;
}
