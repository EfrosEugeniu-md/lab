package org.example.model.priz;

import java.util.ArrayList;
import java.util.List;

public class GroundingMeasurementMapper {

    public static List<GroundingMeasurement> map(
            List<List<String>> rows) {

        List<GroundingMeasurement> result = new ArrayList<>();

        // Общие параметры, которые могут продолжаться
        // в следующих строках Excel
        String[] previous = new String[12];

        for (int i = 1; i < rows.size(); i++) {

            List<String> row = rows.get(i);

            if (row.isEmpty()) {
                continue;
            }

            // Индивидуальные значения каждой строки
            String id = getValue(row, 0);
            String target = getValue(row, 6);
            String depth = getValue(row, 7);
            String resistance = getValue(row, 8);

            if (id.isBlank() && target.isBlank()) {
                continue;
            }

            // Общие параметры с переносом предыдущих значений
            int[] sharedColumns = {1, 2, 3, 4, 5, 9, 10, 11};

            for (int column : sharedColumns) {

                String value = getValue(row, column);

                if (!value.isBlank()) {
                    previous[column] = value;
                }
            }

            GroundingMeasurement measurement =
                    new GroundingMeasurement(
                            id,
                            previous[1],  // Soil
                            previous[2],  // Moisture
                            previous[3],  // Voltage
                            previous[4],  // Soil coefficient
                            previous[5],  // Grounding purpose
                            target,
                            depth,
                            resistance,
                            previous[9],  // Grounding type
                            previous[10], // Size
                            previous[11]  // Thickness
                    );

            result.add(measurement);
        }

        return result;
    }

    private static String getValue(
            List<String> row, int index) {

        if (index >= row.size() || row.get(index) == null) {
            return "";
        }

        return row.get(index).trim();
    }
}

