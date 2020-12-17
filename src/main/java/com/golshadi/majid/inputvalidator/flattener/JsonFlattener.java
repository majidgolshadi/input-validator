package com.golshadi.majid.inputvalidator.flattener;

import static com.github.wnameless.json.flattener.JsonFlattener.flattenAsMap;

import java.util.Map;

public class JsonFlattener implements Flattener {

  @Override
  public Map<String, Object> flat(String json) {
    return flattenAsMap(json);
  }
}
