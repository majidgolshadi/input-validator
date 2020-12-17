package com.golshadi.majid.inputvalidator.service;

public interface DataBinderHandler {

  // set required fields those will be check in bind method
  // {@link org.springframework.validation.DataBinder#setRequiredFields}
  void setRequiredFields(String... requiredFields);

  // check required fields existence
  // {@link org.springframework.validation.DataBinder#bind}
  void bind();

  // execute validation over each parameter
  // {@link org.springframework.validation.DataBinder#validate}
  void validate();

  // get the operation result
  // see {@link org.springframework.validation.DataBinder#getBindingResult}
  BindingResult getBindingResult();
}
