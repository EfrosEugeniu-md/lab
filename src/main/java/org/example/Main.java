package org.example;

import org.example.excelReader.ExcelReader;
import org.example.model.baza.TechnicalReport;
import org.example.model.baza.TechnicalReportMapper;
import org.example.model.circ.Contour;
import org.example.model.circ.MeasurementElement;
import org.example.model.circ.MeasurementTreeBuilder;
import org.example.model.circ.Room;
import org.example.model.shem.ElectricalNode;
import org.example.model.shem.ElectricalTreeBuilder;
import org.example.model.prib.MeasuringInstrument;
import org.example.model.prib.MeasuringInstrumentMapper;
import org.example.model.priz.GroundingMeasurement;
import org.example.model.priz.GroundingMeasurementMapper;
import org.example.wordPress.TechnicalReportGeneratorMy;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {

        List<List<String>> baza = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "BAZA");
        List<List<String>> circ = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "CIRC");
        List<List<String>> shem = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "SHEM");
        List<List<String>> priz = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "PRIZ");
        List<List<String>> trasn = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "TRASN");
        List<List<String>> solul = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "SOLUL");
        List<List<String>> prib = ExcelReader.read("C:/temp/SRL Bruder Unterweger Mol.xlsx", "PRIB");

        TechnicalReport technicalReport= TechnicalReportMapper.map(baza);
        List<MeasuringInstrument> instruments =
                MeasuringInstrumentMapper.map(prib);

        List<GroundingMeasurement> measurements =
                GroundingMeasurementMapper.map(priz);

        ElectricalNode root =
                ElectricalTreeBuilder.build(shem);
        List<Contour> contours = MeasurementTreeBuilder.build(circ);

        TechnicalReportGeneratorMy.generate(
                technicalReport,
            "C:/ELCOM-LUX/Protocols",
            "Protocol_02_2"
            );
        printTree(root,1);
       // printHierarchy(contours);
    }
    public static void printHierarchy(List<Contour> contours) {

        for (Contour contour : contours) {

            System.out.println("\nКОНТУР: " + contour.getName());

            for (Room room : contour.getRooms()) {

                System.out.println("  ПОМЕЩЕНИЕ: " + room.getName());

                for (MeasurementElement element : room.getElements()) {
                    printElement(element, 2);
                }
            }

            for (MeasurementElement element : contour.getElements()) {
                printElement(element, 1);
            }
        }
    }

    private static void printElement(MeasurementElement element, int indent) {

        String prefix = "  ".repeat(indent);

        System.out.printf(
                "%sЭлемент: %s | Измерений: %d | Отрицательных: %d%n",
                prefix,
                element.getName(),
                element.getMeasurementCount(),
                element.getNegativeCount()
        );

        // Если есть ссылка на электрощит — вызываем отдельный метод
        if (element.getElectricalPanel() != null
                && !element.getElectricalPanel().isBlank()) {

            printElectricalPanel(element.getElectricalPanel(), element);
        }

        for (MeasurementElement child : element.getChildren()) {
            printElement(child, indent + 1);
        }
    }

    private static void printElectricalPanel(
            String panelName,
            MeasurementElement element) {

        System.out.println("  ".repeat(element.getLevel() + 2)
                + "ЭЛЕКТРОЩИТ: " + panelName);

        System.out.println("  ".repeat(element.getLevel() + 3)
                + "Название: " + element.getName()
                + " | Измерений: " + element.getMeasurementCount());
    }

    public static void printTree(ElectricalNode node, int level) {

        String indent = "  ".repeat(level);

        System.out.println(indent + node.getName());

        for (ElectricalNode child : node.getChildren()) {
            printTree(child, level + 1);
        }
    }

    public static void printTree(List<ElectricalNode> nodes) {

        for (ElectricalNode node : nodes) {
            printTree(node, 0);
        }
    }
}