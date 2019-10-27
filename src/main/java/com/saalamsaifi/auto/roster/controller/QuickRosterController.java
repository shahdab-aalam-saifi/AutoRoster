package com.saalamsaifi.auto.roster.controller;

import static com.saalamsaifi.auto.roster.config.RosterConfig.DATE_FORMAT;
import static com.saalamsaifi.auto.roster.constant.PathMapping.URL_QUICK_ROSTER;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.END_DATE;
import static com.saalamsaifi.auto.roster.constant.RequestParameter.START_DATE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Table;
import com.saalamsaifi.auto.roster.model.Team;
import com.saalamsaifi.auto.roster.service.ExportService;

@RestController
public class QuickRosterController {
	@Autowired
	private AutoRosterController arc;

	@Autowired
	private ExportService export;

	/**
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	private ResponseEntity<Resource> response(String fileName) throws FileNotFoundException {
		File file = new File(fileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
				.contentLength(file.length()).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	/**
	 * @param team
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 * @throws FileNotFoundException
	 */
	@PostMapping(path = { URL_QUICK_ROSTER }, produces = { MediaType.APPLICATION_JSON_VALUE }, params = { START_DATE,
			END_DATE })
	public ResponseEntity<Resource> generateRoster(@RequestBody @Valid Team team,
			@RequestParam(required = true, name = START_DATE) String strStartDate,
			@RequestParam(required = true, name = END_DATE) String strEndDate) throws FileNotFoundException {
		LocalDate startDate = LocalDate.parse(strStartDate, DATE_FORMAT);
		LocalDate endDate = LocalDate.parse(strEndDate, DATE_FORMAT).plusDays(1);

		Table<String, String, String> table = arc.makeRoster(team, startDate, endDate);

		String fileName = export.getFileName(team, startDate);

		if (export.export(table, fileName)) {
			return response(fileName);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * @param team
	 * @throws IOException
	 */
	@PostMapping(path = { URL_QUICK_ROSTER }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Resource> generateRoster(@RequestBody @Valid Team team) throws IOException {
		LocalDate monday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
		LocalDate saturday = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.SATURDAY));

		Table<String, String, String> table = arc.makeRoster(team, monday, saturday);

		String fileName = export.getFileName(team, monday);

		if (export.export(table, fileName)) {
			return response(fileName);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
