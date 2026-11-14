package org.example.model.shem;

import java.util.ArrayList;
import java.util.List;

public class ElectricalNode {

    private NodeType type;

    private String parent;
    private String name;
    private String alias;

    private String cableType;
    private Integer quantity;
    private Double section;
    private Double resist;

    private String protectionType;
    private String calculation;
    private Double current;
    private String operationCurrent;
    private String impedance;
    private Double length;

    private List<ElectricalNode> children = new ArrayList<>();

    public void addChild(ElectricalNode child) {
        children.add(child);
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCableType() {
        return cableType;
    }

    public void setCableType(String cableType) {
        this.cableType = cableType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSection() {
        return section;
    }

    public void setSection(Double section) {
        this.section = section;
    }

    public Double getResist() {
        return resist;
    }

    public void setResist(Double resist) {
        this.resist = resist;
    }

    public String getProtectionType() {
        return protectionType;
    }

    public void setProtectionType(String protectionType) {
        this.protectionType = protectionType;
    }

    public String getCalculation() {
        return calculation;
    }

    public void setCalculation(String calculation) {
        this.calculation = calculation;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public String getOperationCurrent() {
        return operationCurrent;
    }

    public void setOperationCurrent(String operationCurrent) {
        this.operationCurrent = operationCurrent;
    }

    public String getImpedance() {
        return impedance;
    }

    public void setImpedance(String impedance) {
        this.impedance = impedance;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public List<ElectricalNode> getChildren() {
        return children;
    }

    public void setChildren(List<ElectricalNode> children) {
        this.children = children;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
