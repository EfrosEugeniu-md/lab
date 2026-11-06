package org.example.wordPress;


import org.apache.poi.xwpf.usermodel.*;
import org.example.model.baza.TechnicalReport;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TechnicalReportGeneratorMy {


    public static void generate(
            TechnicalReport technicalReport,
            String outputFolder,
            String fileName
    ) throws Exception {

        Path folder = Path.of(outputFolder);
        Files.createDirectories(folder);

        if (!fileName.toLowerCase().endsWith(".docx")) {
            fileName += ".docx";
        }

        Path output = folder.resolve(fileName);

        try (XWPFDocument doc = new XWPFDocument()) {

            // Page settings
            var settings = doc.getDocument().getBody().addNewSectPr();
            var pageSize = settings.addNewPgSz();

            pageSize.setW(11906);
            pageSize.setH(16838);

            var margins = settings.addNewPgMar();
            margins.setTop(650);
            margins.setBottom(650);
            margins.setLeft(850);
            margins.setRight(850);

            // Default font
            doc.createStyles();

            // =================================================
            // LABORATORY HEADER
            // =================================================

            addText(doc, "LABORATORUL ELECTROTEHNIC", ParagraphAlignment.CENTER,
                    true,false,false,16);
            addText(doc, "de măsurări și încercări electrice", ParagraphAlignment.CENTER,
                    true,false,false,16);
            addText(doc, technicalReport.getLaboratoryName(), ParagraphAlignment.CENTER,
                    true,false,true,16);
            addText(doc, "denumirea laboratorului electrotehnic", ParagraphAlignment.CENTER,
                    false,false,false,7);
            addText(doc, technicalReport.getLaboratoryAddress(), ParagraphAlignment.CENTER,
                    false,false,true,12);
            addText(doc, "adresa, telefon", ParagraphAlignment.CENTER,
                    false,false,false,7);

           addBlank(doc, 1);

            addText(doc, "Autorizația cu privire la laboratorul electrotehnic nr. "
                            +technicalReport.getLaboratoryNumber(), ParagraphAlignment.CENTER,
                    false,false,false,12);
            addText(doc, "valabilă până la "
                            +technicalReport.getLaboratoryValidUntil(), ParagraphAlignment.CENTER,
                    false,false,false,12);

//
//            addCenteredBold(doc,
//                    "Autorizația cu privire la laboratorul electrotehnic nr. "
//                            + data.authorizationNumber(), 11);
//
//            addCenteredBold(doc,
//                    "valabilă până la " + data.authorizationValidUntil(), 11);
//
//            addBlank(doc, 1);
//
//            addCenteredBold(doc, "A P R O B", 12);
//
//            addCentered(doc,
//                    "Director " + data.directorName(), 11);
//
//            addCentered(doc,
//                    "________________ / " + data.approvalDate() + " /", 10);
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // REPORT TITLE
//            // =================================================
//
//            addCenteredBold(doc,
//                    "RAPORT TEHNIC nr. " + data.reportNumber()
//                            + " din " + data.reportDate(), 13);
//
//            addCenteredBold(doc,
//                    "cu privire la efectuarea măsurărilor și încercărilor", 11);
//
//            addCenteredBold(doc,
//                    "de reglare-recepție și profilactice", 11);
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // CUSTOMER
//            // =================================================
//
//            addBold(doc, "PROPRIETAR:");
//
//            addParagraph(doc,
//                    data.objectDescription() + ", "
//                            + data.objectAddress()
//                            + ", NLC " + data.nlc());
//
//            addSmall(doc,
//                    "denumirea agentului economic şi/sau proprietarul instalației electrice; "
//                            + "adresa; tel;");
//
//            addParagraph(doc, data.ownerName());
//
//            addSmall(doc,
//                    "numele administratorului, funcția");
//
//            if (notEmpty(data.administratorName())) {
//                addParagraph(doc,
//                        data.administratorName() + " "
//                                + data.administratorPosition());
//            }
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // MEASUREMENTS
//            // =================================================
//
//            addBold(doc,
//                    "La solicitarea proprietarului/reprezentantului "
//                            + "proprietarului au fost efectuate următoarele lucrări:");
//
//            int index = 1;
//
//            if (data.measurements() != null) {
//
//                for (Measurement measurement : data.measurements()) {
//
//                    addParagraph(doc,
//                            index + ". " + measurement.description()
//                                    + ". proces-verbal nr. "
//                                    + measurement.protocolNumber()
//                                    + " din " + measurement.protocolDate());
//
//                    index++;
//                }
//            }
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // WORKING TEAM
//            // =================================================
//
//            addBold(doc,
//                    "Măsurările şi încercările au fost efectuate "
//                            + "de formația de lucru:");
//
//            if (data.teamMembers() != null) {
//
//                for (TeamMember member : data.teamMembers()) {
//
//                    addParagraph(doc,
//                            member.name() + " – " + member.position());
//                }
//            }
//
//            addSmall(doc,
//                    "(numele, prenumele, funcția)");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // RECEIVED BY CUSTOMER
//            // =================================================
//
//            addParagraph(doc,
//                    "A primit: __________________ "
//                            + data.receivedDate()
//                            + " / " + data.receivedBy()
//                            + " / __________________");
//
//            addSmall(doc,
//                    "data primirii                 "
//                            + "numele, prenumele şi funcția reprezentantului proprietarului"
//                            + "                 semnătura");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // WORK AUTHORIZATION
//            // =================================================
//
//            addParagraph(doc,
//                    "Nr. autorizației (dispoziţiei) de lucru "
//                            + data.workAuthorizationNumber()
//                            + ", admitent " + data.admittingPerson());
//
//            addSmall(doc, "(numele, prenumele, funcția)");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // MEASUREMENT TYPE
//            // =================================================
//
//            addBold(doc, "Tipul măsurărilor şi încercărilor:");
//
//            addParagraph(doc, data.measurementType());
//
//            addSmall(doc,
//                    "(măsurări şi încercări de reglare-recepție "
//                            + "sau periodice)");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // ELECTRICAL INSTALLATION
//            // =================================================
//
//            addBold(doc, "Caracteristica instalației electrice:");
//
//            addParagraph(doc,
//                    "Categoria a instalației electrice "
//                            + data.installationCategory()
//                            + ", modul de racordarea "
//                            + data.connectionType()
//                            + ", sistemul "
//                            + data.groundingSystem());
//
//            addSmall(doc,
//                    "Categoria (I; II; III), modul (LEA; cablu) "
//                            + "şi sistemul (TN-C; TN-S; etc.) de alimentare;");
//
//            addParagraph(doc, data.normativeCompliance());
//
//            addSmall(doc,
//                    "Corespunderea instalației electrice documentației de proiect, "
//                            + "precum și cerințelor documentelor normativ-tehnice;");
//
//            addParagraph(doc, data.protectiveEquipment());
//
//            addSmall(doc,
//                    "Clasa de pericol de incendiu-explozie, tipul aparatului "
//                            + "de protecție contra curenților de scurtcircuit, "
//                            + "corespunderea învelișurilor cablurilor conform "
//                            + "clasei funcționale");
//
//            addParagraph(doc, data.safetyCompliance());
//
//            addSmall(doc,
//                    "Respectarea cerințelor de securitate; etc.");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // ANNEXES
//            // =================================================
//
//            addBold(doc,
//                    "Procesele verbale și schema instalației electrice "
//                            + "sunt anexate pe "
//                            + data.annexSheets() + " foi.");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // DEFICIENCIES
//            // =================================================
//
//            addBold(doc, "Neajunsurile depistate:");
//
//            addItalicParagraph(doc, data.deficiencies());
//
//            addSmall(doc,
//                    "(se notează, cu referire la punctul documentului "
//                            + "normativ-tehnic, toate neajunsurile depistate "
//                            + "în rezultatul inspectării vizuale, măsurărilor "
//                            + "și încercărilor)");
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // RECOMMENDATIONS
//            // =================================================
//
//            addBold(doc, "Recomandări:");
//
//            addItalicParagraph(doc, data.recommendations());
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // CONCLUSIONS
//            // =================================================
//
//            addBold(doc, "Concluzii:");
//
//            addItalicParagraph(doc, data.conclusions());
//
//            addBlank(doc, 1);
//
//            // =================================================
//            // SIGNATURES
//            // =================================================
//
//            addParagraph(doc,
//                    "Șeful laboratorului electrotehnic:     / "
//                            + data.laboratoryHead()
//                            + " / __________________");
//
//            addSmall(doc,
//                    "numele, prenumele                         semnătura");
//
//            addBlank(doc, 1);
//
//            addParagraph(doc,
//                    "A primit: ______________ "
//                            + data.receivedDate()
//                            + " / " + data.customerSignatureName()
//                            + " / ______________");
//
//            addSmall(doc,
//                    "data primirii                 "
//                            + "numele, prenumele şi funcția reprezentantului proprietarului"
//                            + "                 semnătura");

            // =================================================
            // SAVE DOCUMENT
            // =================================================

            try (OutputStream out = Files.newOutputStream(output)) {
                doc.write(out);
            }
        }

        System.out.println(
                "Word document created: " + output.toAbsolutePath());
    }



    // =====================================================
    // TEXT FORMATTING METHODS
    // =====================================================


    private static void addParagraph(
            XWPFDocument doc,
            String text
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.BOTH);
        paragraph.setSpacingAfter(30);

        addRun(paragraph, text, false, false, 11);
    }

    private static void addBold(
            XWPFDocument doc,
            String text
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setSpacingBefore(50);
        paragraph.setSpacingAfter(40);

        addRun(paragraph, text, true, false, 11);
    }

    private static void addTitle(
            XWPFDocument doc,
            String text,
            int size
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        addRun(paragraph, text, true, false, size);
    }

    private static void addCentered(
            XWPFDocument doc,
            String text,
            int size
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        addRun(paragraph, text, false, false, size);
    }



    private static void addCenteredBold(
            XWPFDocument doc,
            String text,
            int size
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        addRun(paragraph, text, true, false, size);
    }

    private static void addSmall(
            XWPFDocument doc,
            String text
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.LEFT);

        paragraph.setSpacingAfter(40);

        addRun(paragraph, text, false, true, 8);
    }

    private static void addItalicParagraph(
            XWPFDocument doc,
            String text
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.BOTH);

        addRun(paragraph, text, false, true, 11);
    }

    private static void addBlank(
            XWPFDocument doc,
            int count
    ) {

        for (int i = 0; i < count; i++) {
            doc.createParagraph();
        }
    }

    private static void addRun(
            XWPFParagraph paragraph,
            String text,
            boolean bold,
            boolean italic,
            int fontSize
    ) {

        XWPFRun run = paragraph.createRun();

        run.setFontFamily("Times New Roman");
        run.setFontSize(fontSize);
        run.setBold(bold);
        run.setItalic(italic);
        run.setText(text == null ? "" : text);
    }

    private static void addText(
            XWPFDocument doc,
            String text,
            ParagraphAlignment paragraphAlignment,
            boolean bold,
            boolean italic,
            boolean underline,
            int fontSize
    ) {
        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(paragraphAlignment);
        XWPFRun run = paragraph.createRun();

        run.setFontFamily("Times New Roman");
        run.setFontSize(fontSize);
        run.setBold(bold);
        run.setItalic(italic);
        run.setUnderline(underline
                ? UnderlinePatterns.SINGLE
                : UnderlinePatterns.NONE);
        run.setText(text == null ? "" : text);
    }

    private static boolean notEmpty(String value) {
        return value != null && !value.isBlank();
    }
}
