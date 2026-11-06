package org.example.model.baza;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechnicalReportMapper {

    public static TechnicalReport map(List<List<String>> rows) {

        Map<String, String> data = new HashMap<>();

        // Преобразуем Excel в Map
        for (List<String> row : rows) {

            if (row.size() < 2) {
                continue;
            }

            String key = row.get(0).trim();
            String value = row.get(1).trim();

            if (!key.isBlank()) {
                data.put(key, value);
            }
        }

        // Создаём сущность
        TechnicalReport report = new TechnicalReport();

        report.setEconomicAgentName(
                data.get("denumirea agentului economic"));

        report.setOwnerAddress(
                data.get("adresa proprietarul"));

        report.setOwnerPhone(
                data.get("tel proprietarul"));

        report.setAdministratorName(
                data.get("numele administratorului"));

        report.setAdministratorPosition(
                data.get("funcția administratorului"));

        report.setAdmittingPerson(
                data.get("Admitentul"));

        report.setWorkPermitNumber(
                data.get("Nr. autorizației de lucru"));

        report.setObjectName(
                data.get("Obiectul"));

        report.setReportNumber(
                data.get("RAPORT TEHNIC nr."));

        report.setMeasurementPurpose(
                data.get("Necesitatea măsurării"));

        report.setApprovalDate(
                parseDate(data.get("Aprobat")));

        report.setMeasurementDate(
                parseDate(data.get("Data efectuarii măsurărilor")));

        report.setAirTemperature(
                (int) parseDouble(data.get("Temperatura aerului")));

        report.setAirHumidity(
                (int) parseDouble(data.get("Umiditatea aerului")));

        report.setSupplyCategory(
                data.get("Categoria de alimentare"));

        report.setSupplyMode(
                data.get("modul de alimentare"));

        report.setSupplySystem(
                data.get("sistemul de alimentare"));

        report.setHazardClass(
                data.get("Clasa de pericol"));

        report.setEquipmentType(
                data.get("tipul aparatului"));

        report.setLaboratoryName(
                data.get("denumirea laboratorului"));

        report.setLaboratoryAddress(
                data.get("adresa, telefon"));

        report.setLaboratoryNumber(
                data.get("laboratorul electrotehnic nr."));

        report.setLaboratoryValidUntil(
                parseDate(data.get("valabilă până")));

        report.setDirector(
                data.get("Director"));

        report.setLaboratoryManager(
                data.get("Șef de laborator"));

        report.setTeamMember1(
                data.get("membru de echipa"));

        return report;
    }

    private static LocalDate parseDate(String value) {
        return value == null || value.isBlank()
                ? null
                : LocalDate.parse(value);
    }

    private static double parseDouble(String value) {
        return value == null || value.isBlank()
                ? 0.0
                : Double.parseDouble(value);
    }
}
