package com.saalamsaifi.auto.roster.service.impl;

import static com.saalamsaifi.auto.roster.constant.ProjectConstant.SEPARATOR;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Table;
import com.saalamsaifi.auto.roster.service.ExportService;

@Service
public class ExportToExcelServiceImpl implements ExportService {
	private static final String TEAM = "Team";
	private static final String NAME = "Name";

	@Override
	public void export(Table<String, String, String> table, String fileName) {
		Set<String> rowKeys = table.rowKeySet();
		Set<String> colKeys = table.columnKeySet();
		int rowNumber = 0;
		int colNumber = 0;

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet();

			Row headerRow = sheet.createRow(rowNumber++);

			setCellValue(headerRow, colNumber++, TEAM);
			setCellValue(headerRow, colNumber++, NAME);

			for (String date : colKeys) {
				setCellValue(headerRow, colNumber++, date);
			}

			for (String r : rowKeys) {
				colNumber = 0;
				String[] t = r.split(SEPARATOR);
				Row row = sheet.createRow(rowNumber++);
				setCellValue(row, colNumber++, t[0]);
				setCellValue(row, colNumber++, t[1]);

				for (String c : colKeys) {
					setCellValue(row, colNumber++, table.get(r, c));
				}
			}

			autoSizeColumn(sheet, colKeys.size() + 2);
			write(workbook, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setCellValue(final Row row, int colNumber, String value) {
		Cell cell = row.createCell(colNumber);
		if (!StringUtils.isEmpty(value)) {
			cell.setCellValue(value);
		}
	}

	private void write(final Workbook workbook, String fileName) {
		try (FileOutputStream stream = new FileOutputStream(fileName)) {
			workbook.write(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void autoSizeColumn(final Sheet sheet, int size) {
		for (int i = 0; i < size; i++) {
			sheet.autoSizeColumn(i);
		}
	}

}
