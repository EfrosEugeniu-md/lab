package org.example.model.shem;

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

        // 1. Создаём ROOT
        ElectricalNode root = new ElectricalNode();
        root.setType(NodeType.ROOT);
        root.setName("ROOT");

        // 2. Хранилища
        Map<String, ElectricalNode> nodes = new HashMap<>();

        // Связь строки Excel с созданным элементом
        Map<List<String>, ElectricalNode> rowNodes = new IdentityHashMap<>();

        nodes.put("ROOT", root);

        // 3. Определяем имена родительских элементов
        Set<String> parentNames = new HashSet<>();

        for (List<String> row : rows) {

            String parent = get(row, PARENT);
            String name = get(row, NAME);

            if (parent != null) {
                parentNames.add(parent);
                parentNames.add(name);
            }
        }

        // 4. Первый проход: создаём ВСЕ обычные элементы
        for (List<String> row : rows) {

            String name = get(row, NAME);

            if (name == null || name.startsWith("-")) {
                continue;
            }

            ElectricalNode node = createNode(row);

            if (parentNames.contains(name)) {
                node.setType(NodeType.PANEL);
            } else {
                node.setType(NodeType.ELEMENT);
            }

            nodes.put(name, node);
            rowNodes.put(row, node);
        }

        // 5. Второй проход: устанавливаем связи
        ElectricalNode currentParent = root;
        ElectricalNode previousElement = null;

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

            // Вложенные элементы (DETAIL)
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

            // Получаем ранее созданный элемент
            ElectricalNode node = rowNodes.get(row);

            // Присоединяем к родителю
            currentParent.addChild(node);

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

    public static ElectricalNode findPanel(
            ElectricalNode root,
            String panelName) {

        if (root == null) {
            return null;
        }

        if (root.getName().equals("Gradirnea")) {
          int a =0;
        }

        if (root.getType() == NodeType.PANEL
                && root.getName() != null
                && root.getName().equalsIgnoreCase(panelName)) {

            return root;
        }

        for (ElectricalNode child : root.getChildren()) {

            ElectricalNode result = findPanel(child, panelName);

            if (result != null) {
                return result;
            }
        }

        return null;
    }


    public static void printPanel(ElectricalNode panel, int level) {

        if (panel == null) {
            System.out.println("Электрощит не найден");
            return;
        }

        String indent = "  ".repeat(level);

        System.out.println(indent + "ЭЛЕКТРОЩИТ: " + panel.getName());

        for (ElectricalNode child : panel.getChildren()) {

            printNode(child, level + 1);
        }
    }

    private static void printNode(ElectricalNode node, int level) {

        String indent = "  ".repeat(level);

        System.out.println(
                indent + node.getName()
                        + " | Количество: " + node.getQuantity()
                        + " | Сечение: " + node.getSection()
                        + " | Длина: " + node.getLength()
        );

        for (ElectricalNode child : node.getChildren()) {
            printNode(child, level + 1);
        }
    }
}
