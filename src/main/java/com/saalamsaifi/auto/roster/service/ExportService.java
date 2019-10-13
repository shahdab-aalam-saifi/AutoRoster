package com.saalamsaifi.auto.roster.service;

import org.springframework.stereotype.Service;

import com.google.common.collect.Table;

@Service
public interface ExportService {
	/**
	 * @param table
	 */
	void export(Table<String, String, String> table, String fileName);
}
