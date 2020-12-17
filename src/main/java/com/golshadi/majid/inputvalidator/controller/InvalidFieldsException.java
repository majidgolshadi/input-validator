package com.golshadi.majid.inputvalidator.controller;

import java.util.List;

class InvalidFieldsException extends Exception {

  private final String message;

  public InvalidFieldsException(List<String> invalidFields) {
    message = String.format("invalid fields: %s",
        String.join(",", invalidFields)
    );
  }

  @Override
  public String getMessage() {
    return message;
  }
}
