package com.golshadi.majid.inputvalidator.service;

import java.util.List;
import java.util.Map;
import org.springframework.lang.Nullable;
import org.springframework.validation.DirectFieldBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

class FlattenDataBinderHandler implements DataBinderHandler {

  private final ValidatorProvider validatorProvider;
  private final Map<String, Object> flattenData;
  private final BindingResult bindingResult;

  private String[] requiredFields;

  public FlattenDataBinderHandler(ValidatorProvider validatorProvider,
      Map<String, Object> flattenData) {
    this.validatorProvider = validatorProvider;
    this.flattenData = flattenData;

    this.bindingResult = new BindingResult();
  }

  @Override
  public void setRequiredFields(@Nullable String... requiredFields) {
    this.requiredFields = requiredFields;
  }

  @Override
  public void bind() {
    for (String requiredField : requiredFields) {
      if (!flattenData.containsKey(requiredField)) {
        bindingResult.addRequiredError(requiredField);
      }
    }
  }

  @Override
  public void validate() {
    flattenData.forEach((key, value) -> {
      var validatorList = validatorProvider.getValidators(key);

      if (validatorList == null) {
        bindingResult.addNotValidateFields(key);
        return;
      }

      if (!isValid(value, validatorList)) {
        bindingResult.addInvalidFields(key);
      }
    });
  }

  @Override
  public BindingResult getBindingResult() {
    return bindingResult;
  }

  private boolean isValid(Object value, List<Validator> validatorList) {
    return validatorList.stream().allMatch(validator -> validate(validator, value));
  }

  private boolean validate(Validator validator, Object value) {
    Errors errors = new DirectFieldBindingResult(value, validator.getClass().getName());

    try {
      ValidationUtils.invokeValidator(validator, value, errors);
    } catch (IllegalArgumentException illegalArgumentException) {
      return false;
    }

    return !errors.hasErrors();
  }
}
