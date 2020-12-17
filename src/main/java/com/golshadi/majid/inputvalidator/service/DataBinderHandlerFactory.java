package com.golshadi.majid.inputvalidator.service;

public interface DataBinderHandlerFactory<T> {

  DataBinderHandler newDataBinderHandler(ValidatorProvider validatorProvider, T target);
}
