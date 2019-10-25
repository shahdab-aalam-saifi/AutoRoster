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
	private static final String TEAM = "{\"name\":\"Operational Intelligence\",\"maxWfrlAllowed\":2,\"groups\":[{\"name\":\"SECLib\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Sheela Naik\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Shahdab Aalam Saifi\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kashif Jilani\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Vageriya\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Santosh Nayak\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"GIG\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Pankaj Bhandari\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Priyank Sharma\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"APIX\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Robin Phukan\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rajnikant Vanpratiwar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Platform\",\"maxWfrlAllowed\":3,\"members\":[{\"name\":\"Dhiraj DParshionikar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Mishika Srivastava\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kunjan Dagli\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Yogesh Pathak\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akshay Pulugurtha\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Saipada Senapati\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Ibrahim Shaikh\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Amol Bathe\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Abhisekh Kumar\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sandip Gharate\",\"isInterested\":false,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Start\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Shirinivas Karlekar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Bhagat\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"GNE\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Kedar Joshi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Pradeep Patil\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Anish Nama\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Message+\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Dhananjay Karnataki\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Chinmay Jog\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"CIOT\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Prasanna Huggi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Premjeet Kumar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Data Product\",\"maxWfrlAllowed\":2,\"members\":[{\"name\":\"Biswajit Panigrahi\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sanchin Jankar\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Vishal Narkhede\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Suchit Nasre\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Sanjog Mehta\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Rahul Mane\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]},{\"name\":\"Chatbot\",\"maxWfrlAllowed\":1,\"members\":[{\"name\":\"Nandini Kalita\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Kaustav Banerjee\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]},{\"name\":\"Akhsay Udhane\",\"isInterested\":true,\"likes\":[],\"dislikes\":[]}]}]}";

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

	@PostMapping(path = { URL_QUICK_ROSTER }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { START_DATE, END_DATE })
	public void generateRoster(@RequestBody @Valid Team team,
			@RequestParam(required = true, name = START_DATE) String strStartDate,
			@RequestParam(required = true, name = END_DATE) String strEndDate) {
		LocalDate startDate = LocalDate.parse(strStartDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate endDate = LocalDate.parse(strEndDate, DateTimeFormatter.ofPattern("dd/MM/yyyy")).plusDays(1);
		
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

		exportService.export(table,
				team.getName() + "_" + startDate.format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".xlsx");

	}
}
