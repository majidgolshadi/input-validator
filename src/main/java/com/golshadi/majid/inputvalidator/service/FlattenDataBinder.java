package com.golshadi.majid.inputvalidator.service;

import com.golshadi.majid.inputvalidator.service.exception.UndefinedRuleException;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class FlattenDataBinder {

  private final ValidatorProviderResolver validatorProviderResolver;
  private final RequiredFieldResolver requiredFieldResolver;
  private final DataBinderHandlerFactory<Map<String, Object>> handlerFactory;

  public FlattenDataBinder(
      ValidatorProviderResolver validatorProviderResolver,
      RequiredFieldResolver requiredFieldResolver) {
    this.validatorProviderResolver = validatorProviderResolver;
    this.requiredFieldResolver = requiredFieldResolver;

    this.handlerFactory = new FlattenDataBinderHandlerFactory();
  }

  public BindingResult validate(String key, Map<String, Object> data)
      throws UndefinedRuleException {

    try {
      var validatorProvider = validatorProviderResolver.getValidatorProvider(key);
      var requiredFields = requiredFieldResolver.getRequiredFieldProvider(key);
      var handler = handlerFactory.newDataBinderHandler(validatorProvider, data);

      handler.setRequiredFields(requiredFields);

      handler.bind();
      handler.validate();

      return handler.getBindingResult();
    } catch (Exception ex) {
      throw new UndefinedRuleException(
          String.format("undefined rule key %s message: %s", key, ex.getMessage())
      );
    }
  }
}
