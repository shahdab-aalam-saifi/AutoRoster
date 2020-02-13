package com.saalamsaifi.auto.roster.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestUtils {
  private static ObjectMapper mapper;

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
