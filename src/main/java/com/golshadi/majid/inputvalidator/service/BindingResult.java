package com.golshadi.majid.inputvalidator.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class BindingResult {

  private final List<String> requiredError;
  private final List<String> invalidFields;
  private final List<String> notValidateFields;

  public BindingResult() {
    this.requiredError = new ArrayList<>();
    this.invalidFields = new ArrayList<>();
    this.notValidateFields = new ArrayList<>();
  }

  public void addRequiredError(String fieldKey) {
    requiredError.add(fieldKey);
  }

  public void addInvalidFields(String fieldKey) {
    invalidFields.add(fieldKey);
  }

  public void addNotValidateFields(String key) {
    notValidateFields.add(key);
  }

  public int getErrorCount() {
    return invalidFields.size() + requiredError.size() + notValidateFields.size();
  }
}
