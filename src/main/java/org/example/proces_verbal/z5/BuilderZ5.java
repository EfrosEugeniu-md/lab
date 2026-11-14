package org.example.proces_verbal.z5;

import org.example.model.shem.ElectricalNode;

import java.util.ArrayList;
import java.util.List;

import static org.example.model.shem.ElectricalTreeBuilderUtils.getElementsWithDetailsByPanel;
import static org.example.model.shem.ElectricalTreeBuilderUtils.getPanels;

public class BuilderZ5 {

    public static List<ModelZ5> build(ElectricalNode root) {
        List<ModelZ5> result = new ArrayList<>();
        int id = 1;
        String curentPaneli;

        List<ElectricalNode> panelling = getPanels(root);

        for (ElectricalNode paneli : panelling) {

            curentPaneli =paneli.getName();
            List<ElectricalNode> electricalNodes = getElementsWithDetailsByPanel(root, paneli.getName());

            if (electricalNodes.isEmpty()) continue;

            for (ElectricalNode node : electricalNodes) {

                if (node.getChildren().isEmpty()) continue;
                if (!node.getParentName().equals(curentPaneli)) continue;

                ModelZ5 avt = new ModelZ5();
                avt.setEquipmentName(node.getParentName() + " : " + node.getName());
                result.add(avt);

                for (ElectricalNode elm : node.getChildren()) {
                    ModelZ5 e = new ModelZ5();
                    e.setId(id++);
                    e.setEquipmentName(elm.getName());
                    e.setVerifiedElements(Integer.valueOf(elm.getAliasOrNumberOfPoints()));
                    e.setContactType("bulon");
                    e.setResistance(0.1);
                    e.setNormativeResistance(0.1);
                    result.add(e);
                }

            }

        }
        return result;
    }
}











