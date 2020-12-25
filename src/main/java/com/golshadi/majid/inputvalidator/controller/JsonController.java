package com.golshadi.majid.inputvalidator.controller;

import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.golshadi.majid.inputvalidator.configuration.HeaderKeyConfiguration;
import com.golshadi.majid.inputvalidator.service.Validator;
import java.io.IOException;
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
  private final Validator validator;

  public JsonController(
      HeaderKeyConfiguration headerKeyConfiguration,
      Validator validator) {
    this.headerKey = headerKeyConfiguration;
    this.validator = validator;
  }

  @RequestMapping(
      method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH},
      path = "/validate")
  public void validateRequest(HttpServletRequest httpServletRequest,
      @RequestBody String requestBody)
      throws ExtraFieldsException, RequiredFieldsException, InvalidFieldsException, IOException, ProcessingException {

    var ruleKey = httpServletRequest.getHeader(headerKey.getRuleKey());

    var bindingResult = validator.validate(ruleKey, requestBody);

    if (bindingResult.getErrorCount() < 1) {
      return;
    }

    if (!bindingResult.getRequiredError().isEmpty()) {
      throw new RequiredFieldsException(bindingResult.getRequiredError());
    }

    if (!bindingResult.getInvalidFields().isEmpty()) {
      throw new InvalidFieldsException(bindingResult.getInvalidFields());
    }

    if (!bindingResult.getExtraFields().isEmpty()) {
      throw new ExtraFieldsException(bindingResult.getExtraFields());
    }
  }

}
