package com.golshadi.majid.inputvalidator.service;

import java.util.Map;

class FlattenDataBinderHandlerFactory implements DataBinderHandlerFactory<Map<String, Object>> {

  @Override
  public DataBinderHandler newDataBinderHandler(ValidatorProvider validatorProvider, Map<String, Object> target) {
    return new FlattenDataBinderHandler(validatorProvider, target);
  }
}
