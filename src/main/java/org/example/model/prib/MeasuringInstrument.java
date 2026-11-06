package org.example.model.prib;


import java.time.LocalDate;

public class MeasuringInstrument {

    private String name;                 // Denumirea aparatului
    private String serialNumber;         // Numărul de serie
    private String measurementRange;     // Domeniul de măsurare
    private String accuracyClass;        // Clasa de precizie
    private LocalDate verificationDate;  // Data verificării
    private LocalDate validUntil;        // Valabil până
    private String certificateNumber;    // Numărul certificatului
    private String metrologyOrganization; // Organizația emitentă

    public MeasuringInstrument() {
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMeasurementRange() {
        return measurementRange;
    }

    public void setMeasurementRange(String measurementRange) {
        this.measurementRange = measurementRange;
    }

    public String getAccuracyClass() {
        return accuracyClass;
    }

    public void setAccuracyClass(String accuracyClass) {
        this.accuracyClass = accuracyClass;
    }

    public LocalDate getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDate verificationDate) {
        this.verificationDate = verificationDate;
    }

    public LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getMetrologyOrganization() {
        return metrologyOrganization;
    }

    public void setMetrologyOrganization(String metrologyOrganization) {
        this.metrologyOrganization = metrologyOrganization;
    }
}
