package com.golshadi.majid.inputvalidator.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// This class created to show how we can handle multiple types of requests
@RestController
@RequestMapping(consumes = "application/xml")
public class XmlController {

  @PostMapping("/validate")
  public boolean isRequestValid() {
    return true;
  }
}
