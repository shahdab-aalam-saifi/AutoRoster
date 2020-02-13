package com.saalamsaifi.auto.roster.util;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {
  private static ObjectMapper mapper;

  private TestUtils() {
    super();
  }
  
  public static void setObjectMapper(ObjectMapper _mapper) {
    if (mapper == null && _mapper != null) {
      mapper = _mapper;
    }
  }

  public static Object stringToJsonObject(String json, Class<? extends Object> _class) {
    if (mapper == null) {
      throw new IllegalStateException("mapper == null");
    }

    try {
      return mapper.readValue(json, _class);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
