package com.saalamsaifi.auto.roster.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.model.WfrlAllocation;
import com.saalamsaifi.auto.roster.utils.Utils;

@RestController
public class RosterController {
	private static final String TEAM = "{\"name\":\"Operational Intelligence\",\"maxWfrlAllowed\":2,\"groups\":[{\"name\":\"SECLib\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Shahdab\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sheela\",\"isInterested\":true,\"likes\":[\"02/10\"],\"dislikes\":[]}]},{\"name\":\"Platform\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Mishika\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akshay\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Data Product\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Suchit\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Vishal\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]}]}";

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

		List<String> days = Arrays.asList("01/10", "02/10", "03/10", "04/10", "05/10");

		System.out.println("===================== Unallocated Wfrl =====================");
		for (String day : days) {
			int unallocatedWfrl = wfrlAllocation.allocateWfrl(day);
			System.out.println(day + " -> " + unallocatedWfrl);
		}

		Set<String> memberNames = wfrlAllocation.getMemberWfrlAllocations().keySet();

		System.out.println("===================== Wfrl Allocation =====================");
		for (String memberName : memberNames) {
			System.out.println(memberName + " -> " + wfrlAllocation.getMemberWfrlAllocations().get(memberName));
		}
	}
}
