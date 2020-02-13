package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.ProjectConstant.NO_WFRL;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.WFRL;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.model.WfrlAllocation;
import java.time.LocalDate;
import java.util.Set;
import org.springframework.stereotype.Controller;

@Controller
public class AutoRosterController {
  public Table<String, String, String> makeRoster(
      Team team, LocalDate startDate, LocalDate endDate) {
    WfrlAllocation wfrlAllocation = new WfrlAllocation(team);

    Table<String, String, String> table = TreeBasedTable.create();

    LocalDate temp = startDate;
    while (temp.isBefore(endDate)) {
      wfrlAllocation.allocateWfrl(temp, 10);
      temp = temp.plusDays(1);
    }

    temp = startDate;
    while (temp.isBefore(endDate)) {
      Set<String> memberNames = wfrlAllocation.getMemberWfrlAllocations().keySet();

      for (String memberName : memberNames) {
        if (wfrlAllocation.getMemberWfrlAllocations().get(memberName).contains(temp)) {
          table.put(memberName, temp.toString(), WFRL);
        } else {
          table.put(memberName, temp.toString(), NO_WFRL);
        }
      }

      temp = temp.plusDays(1);
    }

    return table;
  }
}
