package com.golshadi.majid.inputvalidator.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BindingResultTests {

  @Test
  public void setAndGetInvalidFieldsTest() {
    String FIELD_KEY = "FIELD_KEY";
    BindingResult bindingResult = new BindingResult();

    bindingResult.addExtraFields(FIELD_KEY);
    assertThat(bindingResult.getExtraFields().get(0)).isEqualTo(FIELD_KEY);

    bindingResult.addInvalidFields(FIELD_KEY);
    assertThat(bindingResult.getInvalidFields().get(0)).isEqualTo(FIELD_KEY);

    bindingResult.addRequiredError(FIELD_KEY);
    assertThat(bindingResult.getRequiredError().get(0)).isEqualTo(FIELD_KEY);
  }

  @Test
  public void getErrorCountTest() {
    String FIELD_KEY = "FIELD_KEY";
    BindingResult bindingResult = new BindingResult();

    bindingResult.addExtraFields(FIELD_KEY);
    assertThat(bindingResult.getErrorCount()).isEqualTo(1);

    bindingResult.addInvalidFields(FIELD_KEY);
    assertThat(bindingResult.getErrorCount()).isEqualTo(2);

    bindingResult.addRequiredError(FIELD_KEY);
    assertThat(bindingResult.getErrorCount()).isEqualTo(3);
  }
}
