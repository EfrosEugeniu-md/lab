package org.example.proces_verbal.z5;

public class ModelZ5 {
        private Integer id;

        private String equipmentName;

        private Integer verifiedElements;

        private String contactType ;

        private Double resistance;

        private Double normativeResistance;

        private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Integer getVerifiedElements() {
        return verifiedElements;
    }

    public void setVerifiedElements(Integer verifiedElements) {
        this.verifiedElements = verifiedElements;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public Double getResistance() {
        return resistance;
    }

    public void setResistance(Double resistance) {
        this.resistance = resistance;
    }

    public Double getNormativeResistance() {
        return normativeResistance;
    }

    public void setNormativeResistance(Double normativeResistance) {
        this.normativeResistance = normativeResistance;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
