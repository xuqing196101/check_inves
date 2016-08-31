package yggc.model;

import java.util.Date;

public class SupplierMatPro {
    private Long id;

    private Long supplierId;

    private String orgName;

    private Long totalPerson;

    private Long totalMange;

    private Long totalTech;

    private Long totalWorker;

    private String scaleTech;

    private String scaleHeightTech;

    private String researchName;

    private Long totalResearch;

    private String countryPro;

    private String countryReward;

    private Long totalBeltline;

    private Long totalDevice;

    private String qcName;

    private Long totalQc;

    private String qcLead;

    private String qcDevice;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Long getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Long totalPerson) {
        this.totalPerson = totalPerson;
    }

    public Long getTotalMange() {
        return totalMange;
    }

    public void setTotalMange(Long totalMange) {
        this.totalMange = totalMange;
    }

    public Long getTotalTech() {
        return totalTech;
    }

    public void setTotalTech(Long totalTech) {
        this.totalTech = totalTech;
    }

    public Long getTotalWorker() {
        return totalWorker;
    }

    public void setTotalWorker(Long totalWorker) {
        this.totalWorker = totalWorker;
    }

    public String getScaleTech() {
        return scaleTech;
    }

    public void setScaleTech(String scaleTech) {
        this.scaleTech = scaleTech == null ? null : scaleTech.trim();
    }

    public String getScaleHeightTech() {
        return scaleHeightTech;
    }

    public void setScaleHeightTech(String scaleHeightTech) {
        this.scaleHeightTech = scaleHeightTech == null ? null : scaleHeightTech.trim();
    }

    public String getResearchName() {
        return researchName;
    }

    public void setResearchName(String researchName) {
        this.researchName = researchName == null ? null : researchName.trim();
    }

    public Long getTotalResearch() {
        return totalResearch;
    }

    public void setTotalResearch(Long totalResearch) {
        this.totalResearch = totalResearch;
    }

    public String getCountryPro() {
        return countryPro;
    }

    public void setCountryPro(String countryPro) {
        this.countryPro = countryPro == null ? null : countryPro.trim();
    }

    public String getCountryReward() {
        return countryReward;
    }

    public void setCountryReward(String countryReward) {
        this.countryReward = countryReward == null ? null : countryReward.trim();
    }

    public Long getTotalBeltline() {
        return totalBeltline;
    }

    public void setTotalBeltline(Long totalBeltline) {
        this.totalBeltline = totalBeltline;
    }

    public Long getTotalDevice() {
        return totalDevice;
    }

    public void setTotalDevice(Long totalDevice) {
        this.totalDevice = totalDevice;
    }

    public String getQcName() {
        return qcName;
    }

    public void setQcName(String qcName) {
        this.qcName = qcName == null ? null : qcName.trim();
    }

    public Long getTotalQc() {
        return totalQc;
    }

    public void setTotalQc(Long totalQc) {
        this.totalQc = totalQc;
    }

    public String getQcLead() {
        return qcLead;
    }

    public void setQcLead(String qcLead) {
        this.qcLead = qcLead == null ? null : qcLead.trim();
    }

    public String getQcDevice() {
        return qcDevice;
    }

    public void setQcDevice(String qcDevice) {
        this.qcDevice = qcDevice == null ? null : qcDevice.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}