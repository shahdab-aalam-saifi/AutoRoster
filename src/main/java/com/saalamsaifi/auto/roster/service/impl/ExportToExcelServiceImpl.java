package com.saalamsaifi.auto.roster.service.impl;

import static com.saalamsaifi.auto.roster.constant.ProjectConstant.SEPARATOR;

import com.google.common.collect.Table;
import com.saalamsaifi.auto.roster.config.RosterConfig;
import com.saalamsaifi.auto.roster.service.ExportService;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ExportToExcelServiceImpl implements ExportService {
  private static final String TEAM = "Team";
  private static final String NAME = "Name";

  /**
   * @param table
   * @param fileName
   * @return
   */
  @Override
  public boolean export(Table<String, String, String> table, String fileName) {
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
        setCellValue(
            headerRow, colNumber++, LocalDate.parse(date).format(RosterConfig.DATE_FORMAT));
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

      int columns = colKeys.size() + 2;
      int rows = rowKeys.size();

      // Formating of output
      autoSizeColumn(sheet, columns);
      mergeCell(sheet, rows, 0);
      styleRow(sheet, 0, columns);

      // Write output to file
      write(workbook, fileName);

      return true;
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFoundException: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    }

    return false;
  }

  /**
   * @param row
   * @param colNumber
   * @param value
   */
  private void setCellValue(final Row row, int colNumber, String value) {
    Cell cell = row.createCell(colNumber);
    if (!StringUtils.isEmpty(value)) {
      cell.setCellValue(value);
    }
  }

  /**
   * @param workbook
   * @param fileName
   * @return
   */
  private void write(final Workbook workbook, String fileName) {
    try (FileOutputStream stream = new FileOutputStream(fileName)) {
      workbook.write(stream);
    } catch (FileNotFoundException e) {
      System.err.println("FileNotFoundException: " + e.getMessage());
    } catch (IOException e) {
      System.err.println("IOException: " + e.getMessage());
    }
  }

  /**
   * @param sheet
   * @param size
   */
  private void autoSizeColumn(final Sheet sheet, int size) {
    for (int i = 0; i < size; i++) {
      sheet.autoSizeColumn(i);
    }
  }

  /**
   * @param sheet
   * @param rows
   * @param column
   */
  private void mergeCell(final Sheet sheet, int rows, int column) {
    int i = 0;

    while (i <= rows) {
      Cell cell = sheet.getRow(i).getCell(column);
      while (i + 1 <= rows
          && cell.getStringCellValue()
              .equals(sheet.getRow(i + 1).getCell(column).getStringCellValue())) {
        i++;
      }
      if (i - cell.getRowIndex() > 0) {
        sheet.addMergedRegion(new CellRangeAddress(cell.getRowIndex(), i, column, column));
        cell.setCellStyle(getCenterAlignment(sheet));
      }
      i++;
    }
  }

  /**
   * @param sheet
   * @return
   */
  private CellStyle getCenterAlignment(final Sheet sheet) {
    Workbook workbook = sheet.getWorkbook();
    CellStyle style = workbook.createCellStyle();

    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);

    return style;
  }

  /** @param row */
  private void styleRow(final Sheet sheet, int row, int columns) {
    Workbook workbook = sheet.getWorkbook();

    Font font = workbook.createFont();
    font.setBold(true);

    CellStyle style = getCenterAlignment(sheet);
    style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setFont(font);

    for (int i = 0; i < columns; i++) {
      sheet.getRow(row).getCell(i).setCellStyle(style);
    }
  }
}
