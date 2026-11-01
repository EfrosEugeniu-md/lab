package org.example.excelReader;


import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.math.BigInteger;
import java.util.List;

public class ProtocolContinuityGenerator {

    // =====================================================
    // DATA MODELS
    // =====================================================

    public record Instrument(
            String type,
            String serialNumber,
            String measuringRange,
            String accuracyClass,
            String lastVerification,
            String nextVerification,
            String certificateNumber,
            String verificationOrganization
    ) {}

    public record MeasurementResult(
            String equipment,
            String circuitCondition,
            String contactType,
            int totalContacts,
            int satisfactoryContacts,
            String note
    ) {}

    public record ProtocolData(
            // Laboratory
            String authorizationNumber,
            String authorizationDate,
            String validUntil,
            String laboratoryHead,
            String approvalDate,

            // Customer
            String customer,
            String objectName,
            String address,
            String measurementDate,

            // Work authorization
            String workAuthorization,
            String admittingPerson,

            // Protocol
            String protocolNumber,
            String protocolTitle,

            // Weather
            String airTemperature,
            String airHumidity,

            // Measurement type
            String measurementPurpose,

            // Instruments
            List<Instrument> instruments,

            // Measurement results
            List<MeasurementResult> results,

            // Conclusion
            String conclusion,

            // Team
            String teamLeader,
            String teamMember,
            String receivedBy,

            // Footer
            String footerTitle,
            String pageNumber
    ) {}

    // =====================================================
    // GENERATE DOCUMENT
    // =====================================================

    public static void generate(
            ProtocolData data,
            String folderPath,
            String fileName
    ) throws Exception {

        Path folder = Path.of(folderPath);
        Files.createDirectories(folder);

        if (!fileName.toLowerCase().endsWith(".docx")) {
            fileName += ".docx";
        }

        Path output = folder.resolve(fileName);

        try (XWPFDocument doc = new XWPFDocument()) {

            configurePage(doc);

            // HEADER
            centered(doc, "LABORATORUL ELECTROTEHNIC", 14, true);

            centered(doc,
                    "Nr. " + data.authorizationNumber()
                            + " din " + data.authorizationDate(),
                    11, true);

            centered(doc,
                    "Valabil până la «" + data.validUntil() + "»",
                    11, false);

            blank(doc);

            paragraph(doc, "Aprobat:", true, false);

            paragraph(doc,
                    "Șef laborator _____________________",
                    false, false);

            centered(doc,
                    "/ " + data.laboratoryHead() + " /",
                    11, false);

            centered(doc,
                    "«" + data.approvalDate() + "»",
                    11, false);

            blank(doc);

            // CUSTOMER INFORMATION
            paragraph(doc,
                    "Beneficiarul: " + data.customer(),
                    true, false);

            paragraph(doc,
                    "Obiectul: " + data.objectName(),
                    false, false);

            paragraph(doc,
                    "Adresa: " + data.address(),
                    false, false);

            paragraph(doc,
                    "Data efectuării măsurărilor: "
                            + data.measurementDate(),
                    false, false);

            paragraph(doc,
                    "Nr. autorizației (dispoziției) de lucru "
                            + data.workAuthorization()
                            + ", Admitentul "
                            + data.admittingPerson(),
                    false, false);

            blank(doc);

            // PROTOCOL TITLE
            centered(doc,
                    "PROCES-VERBAL nr. " + data.protocolNumber(),
                    14, true);

            centered(doc,
                    data.protocolTitle(),
                    12, true);

            blank(doc);

            // WEATHER CONDITIONS
            paragraph(doc,
                    "Condițiile meteorologice la efectuarea măsurărilor:",
                    true, false);

            paragraph(doc,
                    "Temperatura aerului "
                            + data.airTemperature()
                            + " °С. Umiditatea aerului "
                            + data.airHumidity() + " %.",
                    false, false);

            paragraph(doc,
                    "Necesitatea măsurării: "
                            + data.measurementPurpose() + ".",
                    false, false);

            blank(doc);

            // =================================================
            // SECTION 2 - MEASURING INSTRUMENTS
            // =================================================

            paragraph(doc,
                    "2. Măsurările au fost efectuate cu mijloacele de măsurare:",
                    true, false);

            createInstrumentTable(doc, data.instruments());

            blank(doc);

            // =================================================
            // SECTION 3 - MEASUREMENT RESULTS
            // =================================================

            paragraph(doc,
                    "3. Rezultatele măsurărilor:",
                    true, false);

            createResultsTable(doc, data.results());

            blank(doc);

            // FOOTNOTE
            paragraph(doc,
                    "* - În cazul când circuitul electric dintre priza "
                            + "de pământ și instalația electrică legată "
                            + "la pământ este integru și satisfăcător "
                            + "se menționează – integru, și invers.",
                    false, true);

            blank(doc);

            // CONCLUSION
            paragraph(doc, "CONCLUZIE:", true, false);

            paragraph(doc, data.conclusion(), false, false);

            blank(doc);

            // SIGNATURES
            paragraph(doc, "Măsurătorile efectuate:", true, false);

            paragraph(doc,
                    "Șef de laborator    "
                            + data.teamLeader()
                            + "        _____________________",
                    false, false);

            paragraph(doc,
                    "Membru de echipă   "
                            + data.teamMember()
                            + "        _____________________",
                    false, false);

            paragraph(doc,
                    "Proces-verbal a primit    "
                            + "____________________ ("
                            + data.receivedBy() + ")",
                    false, false);

            blank(doc);

            // FOOTER
            centered(doc,
                    "PROCES-VERBAL nr. " + data.protocolNumber()
                            + " (" + data.footerTitle() + ")",
                    9, true);

            centered(doc,
                    "Foaia " + data.pageNumber(),
                    9, false);

            // SAVE
            try (OutputStream out = Files.newOutputStream(output)) {
                doc.write(out);
            }
        }

        System.out.println(
                "Created: " + output.toAbsolutePath());
    }

    // =====================================================
    // INSTRUMENT TABLE
    // =====================================================

    private static void createInstrumentTable(
            XWPFDocument doc,
            List<Instrument> instruments
    ) {

        XWPFTable table = doc.createTable();

        table.setWidth("100%");

        XWPFTableRow header1 = table.getRow(0);

        setCell(header1, 0, "Nr.");
        setCell(header1, 1, "Tipul");
        setCell(header1, 2, "Numărul de uzină");
        setCell(header1, 3, "Caracteristici metrologice");
        setCell(header1, 4, "Data verificării");
        setCell(header1, 5, "Nr. buletin de verificare");
        setCell(header1, 6, "Organismul serviciului metrologic de stat");

        // Add grouped header information
        XWPFTableRow header2 = table.createRow();

        setCell(header2, 0, "");
        setCell(header2, 1, "");
        setCell(header2, 2, "");
        setCell(header2, 3, "Diapazonul de măsurare / Clasa de precizie");
        setCell(header2, 4, "Ultimul / Următorul");
        setCell(header2, 5, "");
        setCell(header2, 6, "");

        // Column numbers
        XWPFTableRow numbers = table.createRow();

        String[] columnNumbers = {
                "1", "2", "3", "4", "5", "6", "7"
        };

        for (int i = 0; i < columnNumbers.length; i++) {
            setCell(numbers, i, columnNumbers[i]);
        }

        if (instruments == null) return;

        int index = 1;

        for (Instrument instrument : instruments) {

            XWPFTableRow row = table.createRow();

            setCell(row, 0, String.valueOf(index++));
            setCell(row, 1, instrument.type());
            setCell(row, 2, instrument.serialNumber());
            setCell(row, 3,
                    instrument.measuringRange()
                            + " / " + instrument.accuracyClass());

            setCell(row, 4,
                    instrument.lastVerification()
                            + " / " + instrument.nextVerification());

            setCell(row, 5, instrument.certificateNumber());

            setCell(row, 6, instrument.verificationOrganization());
        }

        formatTable(table, 8);
    }

    // =====================================================
    // RESULTS TABLE
    // =====================================================

    private static void createResultsTable(
            XWPFDocument doc,
            List<MeasurementResult> results
    ) {

        XWPFTable table = doc.createTable();

        table.setWidth("100%");

        XWPFTableRow header = table.getRow(0);

        String[] headers = {
                "Nr. d/o",
                "Amplasarea și denumirea echipamentului electric",
                "Caracteristica circuitului (integru/rupt)",
                "Caracteristica contactului (sudură/bulon)",
                "Numărul contacte verificate",
                "Satisfăcătoare (Rcont ≤ 0,05 Ω)",
                "Notă"
        };

        for (int i = 0; i < headers.length; i++) {
            setCell(header, i, headers[i]);
        }

        // Group header for contacts
        XWPFTableRow group = table.createRow();

        for (int i = 0; i < 7; i++) {
            setCell(group, i, "");
        }

        setCell(group, 4, "Total");
        setCell(group, 5, "Satisfăcătoare");

        // Column numbers
        XWPFTableRow numbers = table.createRow();

        String[] columnNumbers = {
                "1", "2", "3", "4", "5", "6", "7"
        };

        for (int i = 0; i < 7; i++) {
            setCell(numbers, i, columnNumbers[i]);
        }

        if (results != null) {

            int index = 1;

            for (MeasurementResult result : results) {

                XWPFTableRow row = table.createRow();

                setCell(row, 0, String.valueOf(index++));
                setCell(row, 1, result.equipment());
                setCell(row, 2, result.circuitCondition());
                setCell(row, 3, result.contactType());
                setCell(row, 4,
                        String.valueOf(result.totalContacts()));
                setCell(row, 5,
                        String.valueOf(result.satisfactoryContacts()));
                setCell(row, 6, result.note());
            }
        }

        formatTable(table, 8);
    }

    // =====================================================
    // TABLE FORMATTING
    // =====================================================

    private static void setCell(
            XWPFTableRow row,
            int index,
            String text
    ) {

        while (row.getTableCells().size() <= index) {
            row.addNewTableCell();
        }

        XWPFTableCell cell = row.getCell(index);

        cell.removeParagraph(0);

        XWPFParagraph paragraph = cell.addParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();

        run.setFontFamily("Arial");
        run.setFontSize(8);
        run.setText(text == null ? "" : text);

        cell.setVerticalAlignment(
                XWPFTableCell.XWPFVertAlign.CENTER);
    }

    private static void formatTable(
            XWPFTable table,
            int fontSize
    ) {

        for (XWPFTableRow row : table.getRows()) {

            for (XWPFTableCell cell : row.getTableCells()) {

                for (XWPFParagraph paragraph : cell.getParagraphs()) {

                    for (XWPFRun run : paragraph.getRuns()) {
                        run.setFontFamily("Arial");
                        run.setFontSize(fontSize);
                    }
                }
            }
        }
    }

    // =====================================================
    // PAGE SETTINGS
    // =====================================================

    private static void configurePage(XWPFDocument doc) {

        CTSectPr section = doc.getDocument()
                .getBody()
                .addNewSectPr();

        CTPageSz pageSize = section.addNewPgSz();

        // A4 landscape
        pageSize.setW(BigInteger.valueOf(16838));
        pageSize.setH(BigInteger.valueOf(11906));
        pageSize.setOrient(STPageOrientation.LANDSCAPE);

        CTPageMar margins = section.addNewPgMar();

        margins.setTop(BigInteger.valueOf(500));
        margins.setBottom(BigInteger.valueOf(500));
        margins.setLeft(BigInteger.valueOf(500));
        margins.setRight(BigInteger.valueOf(500));
    }

    // =====================================================
    // TEXT HELPERS
    // =====================================================

    private static void paragraph(
            XWPFDocument doc,
            String text,
            boolean bold,
            boolean italic
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingAfter(50);

        XWPFRun run = paragraph.createRun();

        run.setFontFamily("Arial");
        run.setFontSize(10);
        run.setBold(bold);
        run.setItalic(italic);
        run.setText(text == null ? "" : text);
    }

    private static void centered(
            XWPFDocument doc,
            String text,
            int size,
            boolean bold
    ) {

        XWPFParagraph paragraph = doc.createParagraph();

        paragraph.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun run = paragraph.createRun();

        run.setFontFamily("Arial");
        run.setFontSize(size);
        run.setBold(bold);
        run.setText(text == null ? "" : text);
    }

    private static void blank(XWPFDocument doc) {
        doc.createParagraph();
    }
}

