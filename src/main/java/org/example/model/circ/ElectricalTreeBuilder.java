package org.example.model.circ;

import java.util.*;

public class ElectricalTreeBuilder {

    private static final int PARENT = 0;
    private static final int NAME = 1;
    private static final int ALIAS = 2;
    private static final int CABLE_TYPE = 3;
    private static final int QUANTITY = 4;
    private static final int SECTION = 5;
    private static final int LENGTH = 6;
    private static final int PROTECTION = 7;
    private static final int CALCULATION = 8;
    private static final int CURRENT = 9;
    private static final int OPERATION_CURRENT = 10;
    private static final int IMPEDANCE = 11;

    public static ElectricalNode build(List<List<String>> rows) {

        // 1. Создаём корневой элемент
        ElectricalNode root = new ElectricalNode();
        root.setType(NodeType.ROOT);
        root.setName("ROOT");

        // 2. Хранилище созданных элементов
        Map<String, ElectricalNode> nodes = new HashMap<>();
        nodes.put("ROOT", root);

        ElectricalNode currentParent = root;
        ElectricalNode previousElement = null;

        // 3. Определяем все имена родительских элементов
        Set<String> parentNames = new HashSet<>();

        for (List<String> row : rows) {
            String parent = get(row, PARENT);

            if (parent != null && !parent.startsWith("ID ")) {
                parentNames.add(parent);
            }
        }

        // 4. Обрабатываем строки Excel
        for (List<String> row : rows) {

            String parentName = get(row, PARENT);
            String name = get(row, NAME);

            if (name == null) {
                continue;
            }

            // Определяем родителя
            if (parentName != null) {

                if (parentName.startsWith("ID ")) {
                    currentParent = root;
                } else {

                    currentParent = nodes.get(parentName);

                    if (currentParent == null) {
                        throw new IllegalStateException(
                                "Parent not found: " + parentName
                        );
                    }
                }

                previousElement = null;
            }

            // Вложенные элементы
            if (name.startsWith("-")) {

                if (previousElement == null) {
                    throw new IllegalStateException(
                            "No parent element for detail: " + name
                    );
                }

                ElectricalNode detail = createNode(row);
                detail.setType(NodeType.DETAIL);

                previousElement.addChild(detail);

                continue;
            }

            // Создаём элемент
            ElectricalNode node = createNode(row);

            if (parentNames.contains(name)) {
                node.setType(NodeType.PANEL);
            } else {
                node.setType(NodeType.ELEMENT);
            }

            currentParent.addChild(node);

            // Регистрируем элемент
            nodes.put(name, node);

            previousElement = node;
        }

        return root;
    }

    // Создание ElectricalNode из строки Excel
    private static ElectricalNode createNode(List<String> row) {

        ElectricalNode node = new ElectricalNode();

        node.setName(get(row, NAME));
        node.setAlias(get(row, ALIAS));

        node.setCableType(get(row, CABLE_TYPE));
        node.setQuantity(getInteger(row, QUANTITY));
        node.setSection(getDouble(row, SECTION));
        node.setLength(getDouble(row, LENGTH));

        node.setProtectionType(get(row, PROTECTION));
        node.setCalculation(get(row, CALCULATION));
        node.setCurrent(getDouble(row, CURRENT));
        node.setOperationCurrent(get(row, OPERATION_CURRENT));
        node.setImpedance(get(row, IMPEDANCE));

        return node;
    }

    // Получение текстового значения
    private static String get(List<String> row, int index) {

        if (row == null || index >= row.size()) {
            return null;
        }

        String value = row.get(index);

        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    // Получение Integer
    private static Integer getInteger(
            List<String> row,
            int index
    ) {

        String value = get(row, index);

        if (value == null) {
            return null;
        }

        return Integer.valueOf(value);
    }

    // Получение Double
    private static Double getDouble(
            List<String> row,
            int index
    ) {

        String value = get(row, index);

        if (value == null) {
            return null;
        }

        return Double.valueOf(
                value.replace(",", ".")
        );
    }
}
