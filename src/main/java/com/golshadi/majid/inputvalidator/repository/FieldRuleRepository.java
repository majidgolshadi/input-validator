package com.golshadi.majid.inputvalidator.repository;

import com.golshadi.majid.inputvalidator.configuration.FieldValidator;
import com.golshadi.majid.inputvalidator.configuration.RequestValidationConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class FieldRuleRepository {

  private final RequestValidationConfiguration configuration;
  private final Map<String, List<FieldValidator>> datasource;

  public FieldRuleRepository(RequestValidationConfiguration configuration) {
    this.configuration = configuration;

    datasource = new HashMap<>();
  }

  @PostConstruct
  public void postConstruct() {
    convertRequestValidationConfigurationToFiledRule();
  }

  private void convertRequestValidationConfigurationToFiledRule() {
    configuration.getRules().forEach(rule ->
        datasource.put(rule.getKey(), rule.getFields())
    );
  }

  public List<FieldValidator> getRules(String ruleKey) {
    return datasource.get(ruleKey);
  }
}
