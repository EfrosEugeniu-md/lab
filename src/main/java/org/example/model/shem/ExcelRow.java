package org.example.model.shem;

public record ExcelRow(
        String parent,
        String name,
        String alias,
        String cableType,
        Integer quantity,
        Double section,
        Double length,
        String protectionType,
        String calculation,
        Double current,
        String operationCurrent,
        String impedance
) {}
