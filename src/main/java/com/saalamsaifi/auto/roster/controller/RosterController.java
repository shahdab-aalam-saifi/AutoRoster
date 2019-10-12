package com.saalamsaifi.auto.roster.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.model.WfrlAllocation;
import com.saalamsaifi.auto.roster.utils.Utils;

@RestController
public class RosterController {
	private static final String TEAM = "{\"name\":\"Operational Intelligence\",\"maxWfrlAllowed\":2,\"groups\":[{\"name\":\"SECLib\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Shahdab\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sheela\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Platform\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Mishika\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akshay\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Data Product\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Suchit\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Vishal\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]}]}";

	@Autowired
	private ObjectMapper mapper;

	@NonNull
	private <T> List<T> randomCopy(List<T> list) {
		if (list == null) {
			return new ArrayList<>();
		}

		List<T> target = new ArrayList<>(list);
		Collections.shuffle(target, new Random(System.currentTimeMillis()));

		return target;
	}

	@GetMapping(path = { "/" })
	public void get() {
		Team team = Utils.stringToObject(TEAM, mapper, Team.class);

		if (team == null) {
			System.out.println("team == null");
			return;
		}

		WfrlAllocation wfrlAllocation = new WfrlAllocation(team);

		LocalDate startDate = LocalDate.now().minusDays(10);
		LocalDate stopDate = LocalDate.now().plusDays(1);
		Table<String, String, String> table = HashBasedTable.create();

		LocalDate temp = startDate;
		while (temp.isBefore(stopDate)) {
			wfrlAllocation.allocateWfrl(temp);

			temp = temp.plusDays(1);
		}

		temp = startDate;
		while (temp.isBefore(stopDate)) {
			Set<String> memberNames = wfrlAllocation.getMemberWfrlAllocations().keySet();

			for (String memberName : memberNames) {
				if (wfrlAllocation.getMemberWfrlAllocations().get(memberName).contains(temp)) {
					table.put(memberName, temp.toString(), "WFH");
				} else {
					table.put(memberName, temp.toString(), "X");
				}
			}

			temp = temp.plusDays(1);
		}

		Set<String> row = table.rowKeySet();
		Set<String> col = table.columnKeySet();

		for (String c : col) {
			System.out.print(c + ",");
		}
		System.out.println();

		for (String r : row) {
			System.out.print(r + ",");
			for (String c : col) {
				System.out.print(table.get(r, c) + ",");
			}
			System.out.println();
		}

//		List<ImmutableList<Object>> l = table.rowMap().entrySet().stream()
//				.map(entry -> ImmutableList.builder().add(entry.getKey()).addAll(entry.getValue().values()).build())
//				.collect(Collectors.toList());
//
//		for (ImmutableList<Object> immutableList : l) {
//			for (Object o : immutableList) {
//				System.out.print(o + ",");
//			}
//			System.out.println();
//		}
	}
}
