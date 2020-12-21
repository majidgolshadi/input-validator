package com.golshadi.majid.inputvalidator.utils;

import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.golshadi.majid.inputvalidator.service.BindingResult;

public class ProcessingReportUtils {

  public static BindingResult toBindingResult(ProcessingReport processingReport) {
    final var bindingResult = new BindingResult();

    if (processingReport.isSuccess()) {
      return bindingResult;
    }

    processingReport.forEach(data -> {
      var mapObjectNode = data.asJson();

      if (!mapObjectNode.get("level").asText().equals("error")) {
        return;
      }

      if (mapObjectNode.get("missing") != null) {
        mapObjectNode.get("missing").forEach(value -> bindingResult.addRequiredError(value.asText()));

      } else if (mapObjectNode.get("unwanted") != null) {
        mapObjectNode.get("unwanted").forEach(value -> bindingResult.addExtraFields(value.asText()));

      } else if (mapObjectNode.get("instance") != null) {
        mapObjectNode.get("instance").forEach(value -> {
          var strPointer = value.asText().replace("/", ".");
          bindingResult.addInvalidFields(strPointer);
        });
      }
    });

    return bindingResult;
  }
}
