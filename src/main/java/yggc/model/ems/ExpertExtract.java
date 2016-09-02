package yggc.model.ems;

import java.util.Date;

public class ExpertExtract {
    private String id;

    private String projectName;

    private String projectCode;

    private Long procurementDepId;

    private Date extractionTime;

    private String extractionSites;

    private Short extractTheWay;

    private String extractsTheNumber;

    private String extractingConditions;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public Long getProcurementDepId() {
        return procurementDepId;
    }

    public void setProcurementDepId(Long procurementDepId) {
        this.procurementDepId = procurementDepId;
    }

    public Date getExtractionTime() {
        return extractionTime;
    }

    public void setExtractionTime(Date extractionTime) {
        this.extractionTime = extractionTime;
    }

    public String getExtractionSites() {
        return extractionSites;
    }

    public void setExtractionSites(String extractionSites) {
        this.extractionSites = extractionSites == null ? null : extractionSites.trim();
    }

    public Short getExtractTheWay() {
        return extractTheWay;
    }

    public void setExtractTheWay(Short extractTheWay) {
        this.extractTheWay = extractTheWay;
    }

    public String getExtractsTheNumber() {
        return extractsTheNumber;
    }

    public void setExtractsTheNumber(String extractsTheNumber) {
        this.extractsTheNumber = extractsTheNumber == null ? null : extractsTheNumber.trim();
    }

    public String getExtractingConditions() {
        return extractingConditions;
    }

    public void setExtractingConditions(String extractingConditions) {
        this.extractingConditions = extractingConditions == null ? null : extractingConditions.trim();
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