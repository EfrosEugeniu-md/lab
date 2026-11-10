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

        printContours(contours);
    }

    public static void printContours(List<Contour> contours) {

        for (Contour contour : contours) {

            System.out.println("CONTOUR: " + contour.getName());

            for (Room room : contour.getRooms()) {

                System.out.println("  ROOM: " + room.getName());

                for (MeasurementElement element : room.getElements()) {
                    printElement(element, 2);
                }
            }

            for (MeasurementElement element : contour.getElements()) {
                printElement(element, 1);
            }
        }
    }

    private static void printElement(
            MeasurementElement element,
            int indent
    ) {

        String prefix = "  ".repeat(indent);

        System.out.printf(
                "%s%s | Panel: %s | Measurements: %d | Negative: %d%n",
                prefix,
                element.getName(),
                element.getElectricalPanel(),
                element.getMeasurementCount(),
                element.getNegativeCount()
        );

        for (MeasurementElement child : element.getChildren()) {
            printElement(child, indent + 1);
        }
    }
}