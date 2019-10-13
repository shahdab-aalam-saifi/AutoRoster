package com.saalamsaifi.auto.roster.controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
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
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.model.WfrlAllocation;
import com.saalamsaifi.auto.roster.service.ExportService;
import com.saalamsaifi.auto.roster.utils.Utils;

@RestController
public class RosterController {
	private static final String TEAM = "{\"name\":\"Operational Intelligence\",\"maxWfrlAllowed\":2,\"groups\":[{\"name\":\"SECLib\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Sheela Naik\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kashif Jilani\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Vageriya\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Santosh Nayak\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"GIG\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Pankaj Bhandari\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Priyank Sharma\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"APIX\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Robin Phukan\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rajnikant Vanpratiwar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Platform\",\"maxWfrlAllowed\":3,\"members\":[{\"name\":\"Dhiraj DParshionikar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Mishika Srivastava\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kunjan Dagli\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Yogesh Pathak\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akshay Pulugurtha\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Saipada Senapati\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Ibrahim Shaikh\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Amol Bathe\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Abhisekh Kumar\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sandip Gharate\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Start\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Shirinivas Karlekar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Bhagat\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"GNE\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Kedar Joshi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Pradeep Patil\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Anish Nama\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Message+\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Dhananjay Karnataki\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Chinmay Jog\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"CIOT\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Prasanna Huggi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Premjeet Kumar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Data Product\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Biswajit Panigrahi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sanchin Jankar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Vishal Narkhede\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Suchit Nasre\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sanjog Mehta\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Mane\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Chatbot\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Nandini Kalita\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kaustav Banerjee\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akhsay Udhane\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]}]}";

	@Autowired
	private ObjectMapper mapper;

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

	@GetMapping(path = { "/" })
	public void roster() {
		get(LocalDate.of(2019, Month.OCTOBER, 14), LocalDate.of(2019, Month.OCTOBER, 18).plusDays(1));
		get(LocalDate.of(2019, Month.OCTOBER, 21), LocalDate.of(2019, Month.OCTOBER, 25).plusDays(1));
	}

	public void get(LocalDate startDate, LocalDate stopDate) {
		Team team = Utils.stringToObject(TEAM, mapper, Team.class);

		if (team == null) {
			System.out.println("team == null");
			return;
		}

		WfrlAllocation wfrlAllocation = new WfrlAllocation(team);

		Table<String, String, String> table = TreeBasedTable.create();

		LocalDate temp = startDate;
		while (temp.isBefore(stopDate)) {
			wfrlAllocation.allocateWfrl(temp, 10);
			temp = temp.plusDays(1);
		}

		temp = startDate;
		while (temp.isBefore(stopDate)) {
			Set<String> memberNames = wfrlAllocation.getMemberWfrlAllocations().keySet();

			for (String memberName : memberNames) {
				if (wfrlAllocation.getMemberWfrlAllocations().get(memberName).contains(temp)) {
					table.put(memberName, temp.toString(), "WFH");
				} else {
					table.put(memberName, temp.toString(), "");
				}
			}

			temp = temp.plusDays(1);
		}

		exportService.export(table, team.getName() +"_"+ startDate.format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".xlsx");
	}
}
