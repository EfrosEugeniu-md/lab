package org.example;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.example.excelReader.ExcelReader;
import org.example.wordGenerator.ElectricalDiagramGenerator;
import org.example.wordGenerator.WordGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, InvalidFormatException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }

        ExcelReader.read();
        WordGenerator.createWordWithLineText(
                "C:/temp/documents",
                "Protocol",
                "Лаборатория электрических измерений ELCOM-LUX"
        );

        List<ElectricalDiagramGenerator.Feeder> feeders =
                List.of(

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=15 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=18 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=20 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=10 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=12 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=5 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "ABB SH201L C16",
                                "ППВ 3x2,5",
                                "L=25 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "BA47-29 B10",
                                "ППВ 3x2,5",
                                "L=10 м"
                        ),

                        new ElectricalDiagramGenerator.Feeder(
                                "BA47-29 B10",
                                "ППВ 3x2,5",
                                "L=15 м"
                        )
                );

        ElectricalDiagramGenerator.createWord(
                "C:/temp/diagrams",
                "ElectricalDiagram.docx",
                feeders
        );
    }
}