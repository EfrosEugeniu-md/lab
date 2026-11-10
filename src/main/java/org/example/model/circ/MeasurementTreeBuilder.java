
package org.example.model.circ;

import java.util.*;

public class MeasurementTreeBuilder {

    private static final int CONTOUR = 0;
    private static final int NAME = 1;
    private static final int MEASUREMENTS = 2;
    private static final int NEGATIVE = 3;

    private static final String UNKNOWN_CONTOUR = "Контур не указан";

    public static List<Contour> build(List<List<String>> rows) {

        Map<String, Contour> contours = new LinkedHashMap<>();

        Contour currentContour = null;
        Room currentRoom = null;

        // Индекс 0 = элемент без дефисов
        // Индекс 1 = элемент с одним дефисом
        // Индекс 2 = элемент с двумя дефисами
        // Индекс 3 = элемент с тремя дефисами
        MeasurementElement[] parents = new MeasurementElement[4];

        for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {

            List<String> row = rows.get(rowIndex);

            String contourValue = get(row, CONTOUR);
            String rawName = get(row, NAME);
            String measurementsValue = get(row, MEASUREMENTS);
            String negativeValue = get(row, NEGATIVE);

            if (rawName == null) {
                continue;
            }

            // Определяем количество дефисов
            int level = countLeadingDashes(rawName);

            String name = removeLeadingDashes(rawName);

            // Если третий столбец пустой - это помещение
            if (measurementsValue == null) {

                // Обновляем контур, если он указан
                if (contourValue != null) {
                    currentContour = getContour(
                            contours, contourValue
                    );

                    currentRoom = null;
                }

                // Если контур ещё неизвестен
                if (currentContour == null) {
                    currentContour = getContour(
                            contours, UNKNOWN_CONTOUR
                    );
                }

                currentRoom = new Room(name, currentContour);

                currentContour.addRoom(currentRoom);

                // Новое помещение - новая иерархия элементов
                Arrays.fill(parents, null);

                continue;
            }

            // Если нет дефисов - начало новой группы
            if (level == 0) {

                if (contourValue != null) {

                    if (currentContour == null ||
                            !currentContour.getName().equals(contourValue)) {

                        currentContour = getContour(
                                contours, contourValue
                        );

                        currentRoom = null;
                        Arrays.fill(parents, null);
                    }
                }

                if (currentContour == null) {
                    currentContour = getContour(
                            contours, UNKNOWN_CONTOUR
                    );
                }
            }

            // Один дефис и заполнен первый столбец:
            // первый столбец - название электрощита
            String electricalPanel = null;

            if (level == 1 && contourValue != null) {
                electricalPanel = contourValue;
            }

            int measurementCount = parseNumber(
                    measurementsValue, rowIndex
            );

            int negativeCount = parseNumber(
                    negativeValue, rowIndex
            );

            MeasurementElement element = new MeasurementElement(
                    name,
                    electricalPanel,
                    level,
                    measurementCount,
                    negativeCount
            );

            // Определяем родителя
            if (level == 0) {

                if (currentRoom != null) {
                    currentRoom.addElement(element);
                } else {
                    currentContour.addElement(element);
                }

            } else {

                MeasurementElement parent = parents[level - 1];

                if (parent != null) {

                    parent.addChild(element);

                } else if (level == 1 && currentRoom != null) {

                    // Первый уровень может принадлежать помещению
                    currentRoom.addElement(element);

                } else if (level == 1) {

                    // Если помещение не задано,
                    // элемент относится непосредственно к контуру
                    currentContour.addElement(element);

                } else {

                    throw new IllegalStateException(
                            "Parent not found at row "
                                    + (rowIndex + 1)
                                    + ": " + rawName
                    );
                }
            }

            // Запоминаем элемент для последующих уровней
            parents[level] = element;

            // Удаляем устаревших родителей более глубоких уровней
            for (int i = level + 1; i < parents.length; i++) {
                parents[i] = null;
            }
        }

        return new ArrayList<>(contours.values());
    }

    private static Contour getContour(
            Map<String, Contour> contours,
            String name
    ) {
        return contours.computeIfAbsent(
                name,
                Contour::new
        );
    }

    private static int countLeadingDashes(String value) {

        int count = 0;
        int index = 0;

        while (index < value.length()) {

            char ch = value.charAt(index);

            if (ch == ' ' || ch == '\t') {
                index++;
                continue;
            }

            if (ch == '-' || ch == '–' || ch == '—') {
                count++;
                index++;
                continue;
            }

            break;
        }

        if (count > 3) {
            throw new IllegalArgumentException(
                    "Unsupported nesting level: " + value
            );
        }

        return count;
    }

    private static String removeLeadingDashes(String value) {

        int index = 0;

        while (index < value.length()) {

            char ch = value.charAt(index);

            if (ch == ' ' || ch == '\t' ||
                    ch == '-' || ch == '–' || ch == '—') {
                index++;
            } else {
                break;
            }
        }

        return value.substring(index).strip();
    }

    private static String get(List<String> row, int index) {

        if (row == null || index >= row.size()) {
            return null;
        }

        String value = row.get(index);

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.replace('\u00A0', ' ').strip();
    }

    private static int parseNumber(String value, int rowIndex) {

        if (value == null) {
            return 0;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Invalid number at row "
                            + (rowIndex + 1) + ": " + value,
                    e
            );
        }
    }
}

