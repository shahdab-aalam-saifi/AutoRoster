package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_QUICK_ROSTER;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.NO_WFRL;
import static com.saalamsaifi.auto.roster.constant.ProjectConstant.WFRL;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.END_DATE;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.START_DATE;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.model.WfrlAllocation;
import com.saalamsaifi.auto.roster.service.ExportService;

@RestController
public class RosterController {
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static final DateTimeFormatter FILE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd_MM_yyyy");

	@Autowired
	private ExportService exportService;

	@NonNull
	private <T> List<T> randomCopy(List<T> list) {
		if (list == null) {
			return new ArrayList<>();
		}

		List<T> target = new ArrayList<>(list);
		Collections.shuffle(target, new Random(System.currentTimeMillis()));

		return target;
	}

	@PostMapping(path = { URL_QUICK_ROSTER }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { START_DATE,
			END_DATE })
	public void generateRoster(@RequestBody @Valid Team team,
			@RequestParam(required = true, name = START_DATE) String strStartDate,
			@RequestParam(required = true, name = END_DATE) String strEndDate) {
		LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMAT);
		LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMAT).plusDays(1);

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

		exportService.export(table, team.getName() + "_" + startDate.format(FILE_DATE_FORMAT) + ".xlsx");

	}
}
