package org.example.model.prib;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MeasuringInstrumentMapper {

    public static List<MeasuringInstrument> map(
            List<List<String>> rows) {

        List<MeasuringInstrument> instruments = new ArrayList<>();

        for (List<String> row : rows) {

            // Пропускаем пустые строки
            if (row.isEmpty() || row.get(0).isBlank()) {
                continue;
            }

            MeasuringInstrument instrument = new MeasuringInstrument();

            instrument.setName(getValue(row, 0));
            instrument.setSerialNumber(getValue(row, 1));
            instrument.setMeasurementRange(getValue(row, 2));
            instrument.setAccuracyClass(getValue(row, 3));
            instrument.setVerificationDate(parseDate(getValue(row, 4)));
            instrument.setValidUntil(parseDate(getValue(row, 5)));
            instrument.setCertificateNumber(getValue(row, 6));
            instrument.setMetrologyOrganization(getValue(row, 7));

            instruments.add(instrument);
        }

        return instruments;
    }

    private static String getValue(List<String> row, int index) {
        return index < row.size() && row.get(index) != null
                ? row.get(index).trim()
                : "";
    }

    private static LocalDate parseDate(String value) {
        return value == null || value.isBlank()
                ? null
                : LocalDate.parse(value);
    }
}

