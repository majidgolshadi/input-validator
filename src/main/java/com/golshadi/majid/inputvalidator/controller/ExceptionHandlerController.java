package com.golshadi.majid.inputvalidator.controller;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionHandlerController {

  @ExceptionHandler(InvalidFieldsException.class)
  public ResponseEntity<ErrorResponse> invalidFieldsException(Exception ex, HttpServletRequest request) {
    return makeResponse(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler(RequiredFieldsException.class)
  public ResponseEntity<ErrorResponse> requiredFieldsException(Exception ex, HttpServletRequest request) {
    return makeResponse(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler(ExtraFieldsException.class)
  public ResponseEntity<ErrorResponse> extraFieldsException(Exception ex, HttpServletRequest request) {
    return makeResponse(HttpStatus.BAD_REQUEST, ex);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
    log.error("Caught exception - {}", ex.toString());
    return makeResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex);
  }

  private ResponseEntity<ErrorResponse> makeResponse(HttpStatus status, Exception ex) {
    return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage()), status);
  }
}

@Getter
@ToString
class ErrorResponse {

  private final String timestamp;
  private final int status;
  private final String error;
  private final String message;

  public ErrorResponse(HttpStatus status, String message) {
    this.timestamp = Instant.now().toString();
    this.status = status.value();
    this.error = status.getReasonPhrase();
    this.message = message;
  }
}
