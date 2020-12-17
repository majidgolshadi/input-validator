package com.golshadi.majid.inputvalidator.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "header-keys")
public class HeaderKeyConfiguration {

  public String ruleKey;
}
