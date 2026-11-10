package org.example.model.circ;

import java.util.ArrayList;
import java.util.List;

public class MeasurementElement {

    private String name;

    // Название электрощита из первого столбца
    private String electricalPanel;

    // Уровень вложенности: 0, 1, 2, 3
    private int level;

    // Количество измерений
    private int measurementCount;

    // Количество отрицательных результатов
    private int negativeCount;

    private List<MeasurementElement> children = new ArrayList<>();

    public MeasurementElement(
            String name,
            String electricalPanel,
            int level,
            int measurementCount,
            int negativeCount
    ) {
        this.name = name;
        this.electricalPanel = electricalPanel;
        this.level = level;
        this.measurementCount = measurementCount;
        this.negativeCount = negativeCount;
    }

    public void addChild(MeasurementElement child) {
        children.add(child);
    }

    public String getName() {
        return name;
    }

    public String getElectricalPanel() {
        return electricalPanel;
    }

    public int getLevel() {
        return level;
    }

    public int getMeasurementCount() {
        return measurementCount;
    }

    public int getNegativeCount() {
        return negativeCount;
    }

    public List<MeasurementElement> getChildren() {
        return children;
    }
}
