package com.golshadi.majid.inputvalidator.controller;

import java.util.List;

class RequiredFieldsException extends Exception {

  private final String message;

  public RequiredFieldsException(List<String> requiredFields) {
    message = String.format("required fields %s does not exists",
        String.join(",", requiredFields)
    );
  }

  @Override
  public String getMessage() {
    return message;
  }
}
