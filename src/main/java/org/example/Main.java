package org.example;

import org.example.excelReader.ExcelReader;
import org.example.excelReader.ProtocolContinuityGenerator;
import org.example.wordGenerator.WordGenerator;

import static org.example.wordGenerator.xxx.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception {
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

        extracted();


        extracted1();


        var data = createData();

        ProtocolContinuityGenerator.generate(
    data,
            "C:/ELCOM-LUX/Protocols",
            "Protocol_02_2"
            );}


}