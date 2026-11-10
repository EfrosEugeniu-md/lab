package org.example.model.circ;

import java.util.ArrayList;
import java.util.List;

public class Contour {

    private String name;

    private List<Room> rooms = new ArrayList<>();

    private List<MeasurementElement> elements = new ArrayList<>();

    public Contour(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void addElement(MeasurementElement element) {
        elements.add(element);
    }

    public String getName() {
        return name;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<MeasurementElement> getElements() {
        return elements;
    }
}
