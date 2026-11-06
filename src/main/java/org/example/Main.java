package org.example;

import org.example.excelReader.ExcelReader;
import org.example.excelReader.ProtocolContinuityGenerator;
import org.example.wordGenerator.WordGenerator;

import java.util.List;

import static org.example.wordGenerator.xxx.*;

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