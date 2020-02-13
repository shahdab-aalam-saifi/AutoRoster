package com.saalamsaifi.auto.roster.service;

import static com.saalamsaifi.auto.roster.config.RosterConfig.FILE_DATE_FORMAT;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.XLSX;

import com.google.common.collect.Table;
import com.saalamsaifi.auto.roster.model.Team;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public interface ExportService {
  /** @param table */
  boolean export(Table<String, String, String> table, String fileName);

  /**
   * @param team
   * @param date
   * @return
   */
  default String getFileName(Team team, LocalDate date) {
    return MessageFormat.format(
        "{0}{1}{2}{1}{3}{4}",
        team.getName().replace(" ", "_"),
        "_",
        date.format(FILE_DATE_FORMAT),
        UUID.randomUUID(),
        XLSX);
  }
}
