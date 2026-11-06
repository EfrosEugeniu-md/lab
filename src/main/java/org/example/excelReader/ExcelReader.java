package org.example.excelReader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public static List<List<String>> read(
            String filePath,
            String sheetName) throws IOException {

        List<List<String>> result = new ArrayList<>();

        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName);
            }

            for (int i = sheet.getFirstRowNum();
                 i <= sheet.getLastRowNum(); i++) {

                Row row = sheet.getRow(i);

                List<String> rowData = new ArrayList<>();

                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {

                        Cell cell = row.getCell(
                                j,
                                Row.MissingCellPolicy.RETURN_BLANK_AS_NULL
                        );

                        String value = cell == null
                                ? ""
                                : formatter.formatCellValue(cell);

                        rowData.add(value);
                    }
                }

                result.add(rowData);
            }
        }

        return result;
    }
}