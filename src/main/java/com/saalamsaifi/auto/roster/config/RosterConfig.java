package com.saalamsaifi.auto.roster.config;

import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class RosterConfig {
  private RosterConfig() {}

  private static final String dateFormat = "dd/MM/yyyy";

  private static final String fileDateFormat = "dd_MM_yyyy";

  public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern(dateFormat);
  public static final DateTimeFormatter FILE_DATE_FORMAT =
      DateTimeFormatter.ofPattern(fileDateFormat);
}
