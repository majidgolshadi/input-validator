package com.golshadi.majid.inputvalidator.controller;

import java.util.List;

class ExtraFieldsException extends Exception {

  private final String message;

  public ExtraFieldsException(List<String> extraFields) {
    message = String.format("extra fields exist: %s",
        String.join(",", extraFields)
    );
  }

  @Override
  public String getMessage() {
    return message;
  }
}
