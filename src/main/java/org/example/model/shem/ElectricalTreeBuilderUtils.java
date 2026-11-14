package org.example.model.shem;

import java.util.*;

public class ElectricalTreeBuilderUtils implements ElectricalTreeBuilderString {

    public static ElectricalNode findPanel(
            ElectricalNode root,
            String panelName) {

        if (root == null) {
            return null;
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

    public static List<ElectricalNode> getPanels(ElectricalNode root) {

        List<ElectricalNode> panels = new ArrayList<>();

        if (root == null) {
            return panels;
        }

        for (ElectricalNode child : root.getChildren()) {

            if (child.getType() == NodeType.PANEL) {
                panels.add(child);
            }

            panels.addAll(getPanels(child));
        }

        return panels;
    }

    public static List<ElectricalNode> getElementsWithDetailsByPanel(
            ElectricalNode root,
            String panelName) {

        List<ElectricalNode> result = new ArrayList<>();

        ElectricalNode panel = findPanel(root, panelName);

        if (panel == null) {
            return result;
        }

        collectElementsWithDetails(panel, result);

        return result;
    }

    private static void collectElementsWithDetails(
            ElectricalNode node,
            List<ElectricalNode> result) {

        for (ElectricalNode child : node.getChildren()) {

            if (child.getType() == NodeType.ELEMENT
                    && hasDetails(child)) {

                result.add(child);
            }

            collectElementsWithDetails(child, result);
        }
    }

    private static boolean hasDetails(ElectricalNode node) {

        for (ElectricalNode child : node.getChildren()) {

            if (child.getType() == NodeType.DETAIL) {
                return true;
            }

            if (hasDetails(child)) {
                return true;
            }
        }

        return false;
    }
}
