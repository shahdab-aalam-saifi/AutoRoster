package com.saalamsaifi.auto.roster.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.bson.types.ObjectId;

public class Utils {
  private Utils() {
    super();
  }

  public static String getObjectId() {
    return ObjectId.get().toHexString();
  }

  public static <T> T stringToObject(String json, ObjectMapper mapper, Class<T> clazz) {
    if (mapper == null) {
      throw new IllegalStateException("mapper == null");
    }

    try {
      return mapper.readValue(json, clazz);
    } catch (IOException exception) {
      exception.printStackTrace();
      return null;
    }
  }
}
