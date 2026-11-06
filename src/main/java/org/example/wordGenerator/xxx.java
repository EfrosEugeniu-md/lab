package org.example.wordGenerator;

import org.example.excelReader.ProtocolContinuityGenerator;
import org.example.excelReader.TechnicalReportGenerator;

import java.io.IOException;
import java.util.List;

public class xxx {
    public static ProtocolContinuityGenerator.ProtocolData createData() {
        var data = new ProtocolContinuityGenerator.ProtocolData(

                "112",
                "22.04.2026",
                "21 aprilie 2031",
                "Efros E.",
                "02 iunie 2026",

                "BURDIH EUGENIU PETRU",
                "Casă particulară",
                "Codru, Valea Apelor 4 str-la, 5",
                "29 mai 2026",

                "-",
                "-",

                "02/2",
                "Verificarea continuității electrice dintre priza de pământ și instalațiile legate la pământ (X4)",

                "21",
                "60",

                "măsurări şi încercări de reglare-recepție",

                List.of(
                        new ProtocolContinuityGenerator.Instrument(
                                "М - 372",
                                "9116518",
                                "0,1 - ∞ Ω",
                                "1,5",
                                "09.07.2025",
                                "09.07.2026",
                                "MD 10 3.3 - 659/1025",
                                "Institutul Național de Metrologie"
                        )
                ),

                List.of(
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "Priza de pământ - Panou de evidență CEv-0,4 (PE)",
                                "integru",
                                "sudură",
                                2, 2, ""
                        ),
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "PE – PE Panou de distribuție (PE)",
                                "integru",
                                "bulon",
                                2, 2, ""
                        ),
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "PE – Iluminat existent",
                                "integru",
                                "bulon",
                                2, 2, ""
                        ),
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "PE – Supraveghere Video",
                                "integru",
                                "bulon",
                                4, 4, ""
                        ),
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "PE – Prize existent",
                                "integru",
                                "bulon",
                                12, 12, ""
                        ),
                        new ProtocolContinuityGenerator.MeasurementResult(
                                "PE – PD Conditioner",
                                "integru",
                                "bulon",
                                2, 2, ""
                        )
                ),

                "Starea legăturilor metalice a circuitelor de împământare "
                        + "corespunde cerințelor documentelor normative "
                        + "NE1-01:2019 (Vezi tabelele X.4 și 38) "
                        + "pentru toate punctele indicate în procesul-verbal.",

                "E. Efros",
                "F. Craiev",
                "",

                "Verificarea continuității electrice dintre priza de pământ "
                        + "și instalațiile protejate",
                "1/Foi 1"
        );
        return data;
    }

    public static void extracted1() throws Exception {
        var report = new TechnicalReportGenerator.ReportData(

                // Laboratory
                "LABORATORUL ELECTROTEHNIC",
                "Mun. Chisinau, str. A. Marinescu, 11/2",
                "(022)-24-01-65, 060 425 599",
                "112",
                "21 aprilie 2031",
                "Andrei Munteanu",
                "02.06.2026",

                // Report
                "02",
                "02 iunie 2026",

                // Customer
                "BURDIH EUGENIU PETRU",
                "Codru, Valea Apelor 4 str-la, 5",
                "Casă particulară, obiect existent",
                "7152446",
                "",
                "",

                // Measurements
                List.of(
                        new TechnicalReportGenerator.Measurement(
                                "Măsurarea rezistenței prizei de pământ",
                                "02/1",
                                "02 iunie 2026"
                        ),
                        new TechnicalReportGenerator.Measurement(
                                "Verificarea continuității electrice dintre priza de pământ și instalațiile legate la pământ (X4)",
                                "02/2",
                                "02 iunie 2026"
                        ),
                        new TechnicalReportGenerator.Measurement(
                                "Măsurarea rezistenței de izolație a condensatoarelor și cablurilor de forță",
                                "02/3",
                                "02 iunie 2026"
                        ),
                        new TechnicalReportGenerator.Measurement(
                                "Măsurări ale rezistenței electrice a izolației echipamentelor, aparatelor și liniilor electrice",
                                "02/4",
                                "02 iunie 2026"
                        )
                ),

                // Working team
                List.of(
                        new TechnicalReportGenerator.TeamMember(
                                "E. Efros",
                                "Șef de laborator"
                        ),
                        new TechnicalReportGenerator.TeamMember(
                                "F. Craiev",
                                "membru de echipă"
                        )
                ),

                // Acceptance
                "",
                "",

                // Work authorization
                "-",
                "-",

                // Measurement type
                "măsurări şi încercări de reglare-recepție",

                // Electrical installation
                "III",
                "LEA (PT-1442 fid. 3)",
                "TN-C-S",
                "Corespunde cerințelor documentelor normativ-tehnice",
                "Întreruptor automat BA 47-29 Inom=20 A, învelișurilor cablurilor corespunde cerințelor",
                "Cerințelor de securitate corespunde",

                // Additional information
                "8",
                "S-au înlăturat în procesul efectuării lucrărilor.",
                "Se recomandă exploatarea IE strict conform DNT în vigoare.",
                "Instalația electrică se exploatează în strictă conformitate cu cerințele DNT.",

                // Signatures
                "E. Efros",
                "BURDIH EUGENIU PETRU"
        );

        TechnicalReportGenerator.generate(
                report,
                "C:/ELCOM-LUX/Reports",
                "Raport_Tehnic_02"
        );
    }

    public static void extracted() throws IOException {
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
