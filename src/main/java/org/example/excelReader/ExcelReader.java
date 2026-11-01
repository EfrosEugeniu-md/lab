package org.example.excelReader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    public static void read() {

        String filePath = "C:/temp/employees.xlsx";
        String sheetName = "Employees";

        DataFormatter formatter = new DataFormatter();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                throw new IllegalArgumentException(
                        "Sheet not found: " + sheetName);
            }

            for (Row row : sheet) {

                for (Cell cell : row) {

                    String value =
                            formatter.formatCellValue(cell);

                    System.out.print(value + "\t");
                }

                System.out.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

