package org.example.model.shem;

import java.util.*;

public class ElectricalTreeBuilder implements ElectricalTreeBuilderString {

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

        Set<String> parentNames = getParentNames(rows);

        extracted(rows, parentNames, nodes, rowNodes);

        // 5. Второй проход: устанавливаем связи
        ElectricalNode currentParent = root;
        ElectricalNode previousElement = null;
        ElectricalNode previousPaneli = null;

        for (List<String> row : rows) {

            String parentName = get(row, PARENT);
            String name = get(row, NAME);

            if (isNameNull(name)) continue;

            if (isDetail(row, name, previousElement)) continue;

            // Получаем ранее созданный элемент
            ElectricalNode node = rowNodes.get(row);

            // Определяем родителя
            if (parentName==null && node.getType() == NodeType.PANEL ) {
                currentParent = root;
            } else {

                currentParent = nodes.get(parentName);

                if (currentParent == null && node.getType() == NodeType.PANEL) {
                    currentParent=root;
                }

                if (currentParent == null && node.getType() == NodeType.ELEMENT) {
                    currentParent=previousPaneli;
                }
            }

            // Присоединяем к родителю
            currentParent.addChild(node);
            node.setParentName(currentParent.getName());

            if (node.getType() == NodeType.PANEL){
                previousElement = node;
                previousPaneli = node;
            }

            if (node.getType() == NodeType.ELEMENT){
                previousElement = node;
            }

        }

        return root;
    }

    private static boolean isNameNull(String name) {
        if (name == null) {
            return true;
        }
        return false;
    }

    private static boolean isDetail(List<String> row, String name, ElectricalNode previousElement) {
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

            return true;
        }
        return false;
    }

    private static void extracted(List<List<String>> rows, Set<String> parentNames, Map<String, ElectricalNode> nodes, Map<List<String>, ElectricalNode> rowNodes) {
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
    }

    private static Set<String> getParentNames(List<List<String>> rows) {
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
        return parentNames;
    }


    // Создание ElectricalNode из строки Excel
    private static ElectricalNode createNode(List<String> row) {

        ElectricalNode node = new ElectricalNode();

        node.setName(get(row, NAME));
        node.setAliasOrNumberOfPoints(get(row, ALIAS));

        node.setKabeliMarka(get(row, CABLE_TYPE));
        node.setKabeliKolJilV(getInteger(row, QUANTITY));
        node.setKabeliSecenie(getDouble(row, SECTION));
        node.setKabeliSoprotivlenie(getInteger(row, RESIST));
        node.setKabeliDlina(getInteger(row, LENGTH));

        node.setAvtomatMarka(get(row, PROTECTION));
        node.setAvtomatTipulDeclansatorului(get(row, CALCULATION));
        node.setAvtomatNominaliniiTok(getDouble(row, CURRENT));
        node.setAvtomatTokSrabativaniaOrABCD(get(row, OPERATION_CURRENT));
        node.setAvtomatSoprativleniaFazaNolOrIkz(getDouble(row, IMPEDANCE));


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
