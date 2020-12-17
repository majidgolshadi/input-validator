package com.golshadi.majid.inputvalidator.controller;

import com.golshadi.majid.inputvalidator.configuration.HeaderKeyConfiguration;
import com.golshadi.majid.inputvalidator.flattener.Flattener;
import com.golshadi.majid.inputvalidator.flattener.JsonFlattener;
import com.golshadi.majid.inputvalidator.service.FlattenDataBinder;
import com.golshadi.majid.inputvalidator.service.exception.UndefinedRuleException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(consumes = "application/json")
public class JsonController {

  private final HeaderKeyConfiguration headerKey;
  private final FlattenDataBinder flattenDataBinder;
  private final Flattener flattener;

  public JsonController(
      HeaderKeyConfiguration headerKeyConfiguration,
      FlattenDataBinder flattenDataBinder
  ) {
    this.headerKey = headerKeyConfiguration;
    this.flattenDataBinder = flattenDataBinder;

    this.flattener = new JsonFlattener();
  }

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH},
      path = "/validate")
  public void RequestValid(HttpServletRequest httpServletRequest,
      @RequestBody String requestBody)
      throws UndefinedRuleException, ExtraFieldsException, RequiredFieldsException, InvalidFieldsException {

    var ruleKey = httpServletRequest.getHeader(headerKey.getRuleKey());
    var flatData = flattener.flat(requestBody);

    var bindingResult = flattenDataBinder.validate(ruleKey, flatData);

    if (bindingResult.getErrorCount() < 1) {
      return;
    }

    if (!bindingResult.getRequiredError().isEmpty()) {
      throw new RequiredFieldsException(bindingResult.getRequiredError());
    }

    if (!bindingResult.getInvalidFields().isEmpty()) {
      throw new InvalidFieldsException(bindingResult.getInvalidFields());
    }

    if (!bindingResult.getNotValidateFields().isEmpty()) {
      throw new ExtraFieldsException(bindingResult.getNotValidateFields());
    }
  }

}
