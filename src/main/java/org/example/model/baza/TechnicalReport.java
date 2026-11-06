package org.example.model.baza;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TechnicalReport {

    // Economic agent
    private String economicAgentName;
    private String ownerAddress;
    private String ownerPhone;
    private String administratorName;
    private String administratorPosition;

    // Object and measurements
    private String admittingPerson;
    private String workPermitNumber;
    private String objectName;
    private String reportNumber;
    private String measurementPurpose;
    private LocalDate approvalDate;
    private LocalDate measurementDate;
    private Integer airTemperature;
    private Integer airHumidity;

    // Electrical characteristics
    private String supplyCategory;
    private String supplyMode;
    private String supplySystem;
    private String hazardClass;
    private String equipmentType;

    // Laboratory
    private String laboratoryName;
    private String laboratoryAddress;
    private String laboratoryNumber;
    private LocalDate laboratoryValidUntil;
    private String director;
    private String laboratoryManager;
    private List<String> teamMembers = new ArrayList<>();

    public TechnicalReport() {
    }

    // Getters and Setters

    public String getEconomicAgentName() {
        return economicAgentName;
    }

    public void setEconomicAgentName(String economicAgentName) {
        this.economicAgentName = economicAgentName;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getAdministratorName() {
        return administratorName;
    }

    public void setAdministratorName(String administratorName) {
        this.administratorName = administratorName;
    }

    public String getAdministratorPosition() {
        return administratorPosition;
    }

    public void setAdministratorPosition(String administratorPosition) {
        this.administratorPosition = administratorPosition;
    }

    public String getAdmittingPerson() {
        return admittingPerson;
    }

    public void setAdmittingPerson(String admittingPerson) {
        this.admittingPerson = admittingPerson;
    }

    public String getWorkPermitNumber() {
        return workPermitNumber;
    }

    public void setWorkPermitNumber(String workPermitNumber) {
        this.workPermitNumber = workPermitNumber;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(String reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getMeasurementPurpose() {
        return measurementPurpose;
    }

    public void setMeasurementPurpose(String measurementPurpose) {
        this.measurementPurpose = measurementPurpose;
    }

    public LocalDate getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDate approvalDate) {
        this.approvalDate = approvalDate;
    }

    public LocalDate getMeasurementDate() {
        return measurementDate;
    }

    public void setMeasurementDate(LocalDate measurementDate) {
        this.measurementDate = measurementDate;
    }

    public Integer getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(Integer airTemperature) {
        this.airTemperature = airTemperature;
    }

    public Integer getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(Integer airHumidity) {
        this.airHumidity = airHumidity;
    }

    public String getSupplyCategory() {
        return supplyCategory;
    }

    public void setSupplyCategory(String supplyCategory) {
        this.supplyCategory = supplyCategory;
    }

    public String getSupplyMode() {
        return supplyMode;
    }

    public void setSupplyMode(String supplyMode) {
        this.supplyMode = supplyMode;
    }

    public String getSupplySystem() {
        return supplySystem;
    }

    public void setSupplySystem(String supplySystem) {
        this.supplySystem = supplySystem;
    }

    public String getHazardClass() {
        return hazardClass;
    }

    public void setHazardClass(String hazardClass) {
        this.hazardClass = hazardClass;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public String getLaboratoryName() {
        return laboratoryName;
    }

    public void setLaboratoryName(String laboratoryName) {
        this.laboratoryName = laboratoryName;
    }

    public String getLaboratoryAddress() {
        return laboratoryAddress;
    }

    public void setLaboratoryAddress(String laboratoryAddress) {
        this.laboratoryAddress = laboratoryAddress;
    }

    public String getLaboratoryNumber() {
        return laboratoryNumber;
    }

    public void setLaboratoryNumber(String laboratoryNumber) {
        this.laboratoryNumber = laboratoryNumber;
    }

    public LocalDate getLaboratoryValidUntil() {
        return laboratoryValidUntil;
    }

    public void setLaboratoryValidUntil(LocalDate laboratoryValidUntil) {
        this.laboratoryValidUntil = laboratoryValidUntil;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLaboratoryManager() {
        return laboratoryManager;
    }

    public void setLaboratoryManager(String laboratoryManager) {
        this.laboratoryManager = laboratoryManager;
    }

    public List<String> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(List<String> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public void setTeamMember1(String membruDeEchipa) {

    }
}

