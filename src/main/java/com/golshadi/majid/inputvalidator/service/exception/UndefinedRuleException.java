package com.golshadi.majid.inputvalidator.service.exception;

public class UndefinedRuleException extends Exception {

  public UndefinedRuleException() {
    super();
  }

  public UndefinedRuleException(String message) {
    super(message);
  }

  public UndefinedRuleException(String message, Throwable cause) {
    super(message, cause);
  }

  public UndefinedRuleException(Throwable cause) {
    super(cause);
  }

  protected UndefinedRuleException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
