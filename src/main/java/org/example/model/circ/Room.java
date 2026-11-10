package org.example.model.circ;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private String name;

    private Contour contour;

    private List<MeasurementElement> elements = new ArrayList<>();

    public Room(String name, Contour contour) {
        this.name = name;
        this.contour = contour;
    }

    public void addElement(MeasurementElement element) {
        elements.add(element);
    }

    public String getName() {
        return name;
    }

    public Contour getContour() {
        return contour;
    }

    public List<MeasurementElement> getElements() {
        return elements;
    }
}
