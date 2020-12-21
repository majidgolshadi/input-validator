package com.golshadi.majid.inputvalidator.service;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class BindingResult {

  private final List<String> requiredError;
  private final List<String> invalidFields;
  private final List<String> extraFields;

  public BindingResult() {
    this.requiredError = new ArrayList<>();
    this.invalidFields = new ArrayList<>();
    this.extraFields = new ArrayList<>();
  }

  public void addRequiredError(String fieldKey) {
    requiredError.add(fieldKey);
  }

  public void addInvalidFields(String fieldKey) {
    invalidFields.add(fieldKey);
  }

  public void addExtraFields(String key) {
    extraFields.add(key);
  }

  public int getErrorCount() {
    return invalidFields.size() + requiredError.size() + extraFields.size();
  }
}
