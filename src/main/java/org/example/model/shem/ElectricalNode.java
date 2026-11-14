package org.example.model.shem;

import java.util.ArrayList;
import java.util.List;

public class ElectricalNode {

    private NodeType type;

    private String parentName;
    private String name;
    private String aliasOrNumberOfPoints;

    private String kabeliMarka;
    private Integer kabeliKolJilV;
    private Double kabeliSecenie;
    private Integer kabeliDlina;
    private Integer kabeliSoprotivlenie;

    private String avtomatMarka;
    private String avtomatTipulDeclansatorului;
    private Double avtomatNominaliniiTok;
    private String avtomatTokSrabativaniaOrABCD;
    private Double avtomatSoprativleniaFazaNolOrIkz;


    private List<ElectricalNode> children = new ArrayList<>();

    public void addChild(ElectricalNode child) {
        children.add(child);
    }

    // Getters and setters
    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasOrNumberOfPoints() {
        return aliasOrNumberOfPoints;
    }

    public void setAliasOrNumberOfPoints(String aliasOrNumberOfPoints) {
        this.aliasOrNumberOfPoints = aliasOrNumberOfPoints;
    }

    public String getKabeliMarka() {
        return kabeliMarka;
    }

    public void setKabeliMarka(String kabeliMarka) {
        this.kabeliMarka = kabeliMarka;
    }

    public Integer getKabeliKolJilV() {
        return kabeliKolJilV;
    }

    public void setKabeliKolJilV(Integer kabeliKolJilV) {
        this.kabeliKolJilV = kabeliKolJilV;
    }

    public Double getKabeliSecenie() {
        return kabeliSecenie;
    }

    public void setKabeliSecenie(Double kabeliSecenie) {
        this.kabeliSecenie = kabeliSecenie;
    }

    public Integer getKabeliSoprotivlenie() {
        return kabeliSoprotivlenie;
    }

    public void setKabeliSoprotivlenie(Integer kabeliSoprotivlenie) {
        this.kabeliSoprotivlenie = kabeliSoprotivlenie;
    }

    public String getAvtomatMarka() {
        return avtomatMarka;
    }

    public void setAvtomatMarka(String avtomatMarka) {
        this.avtomatMarka = avtomatMarka;
    }

    public String getAvtomatTipulDeclansatorului() {
        return avtomatTipulDeclansatorului;
    }

    public void setAvtomatTipulDeclansatorului(String avtomatTipulDeclansatorului) {
        this.avtomatTipulDeclansatorului = avtomatTipulDeclansatorului;
    }

    public Double getAvtomatNominaliniiTok() {
        return avtomatNominaliniiTok;
    }

    public void setAvtomatNominaliniiTok(Double avtomatNominaliniiTok) {
        this.avtomatNominaliniiTok = avtomatNominaliniiTok;
    }

    public String getAvtomatTokSrabativaniaOrABCD() {
        return avtomatTokSrabativaniaOrABCD;
    }

    public void setAvtomatTokSrabativaniaOrABCD(String avtomatTokSrabativaniaOrABCD) {
        this.avtomatTokSrabativaniaOrABCD = avtomatTokSrabativaniaOrABCD;
    }

    public Double getAvtomatSoprativleniaFazaNolOrIkz() {
        return avtomatSoprativleniaFazaNolOrIkz;
    }

    public void setAvtomatSoprativleniaFazaNolOrIkz(Double avtomatSoprativleniaFazaNolOrIkz) {
        this.avtomatSoprativleniaFazaNolOrIkz = avtomatSoprativleniaFazaNolOrIkz;
    }

    public Integer getKabeliDlina() {
        return kabeliDlina;
    }

    public void setKabeliDlina(Integer kabeliDlina) {
        this.kabeliDlina = kabeliDlina;
    }

    public List<ElectricalNode> getChildren() {
        return children;
    }

    public void setChildren(List<ElectricalNode> children) {
        this.children = children;
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }
}
