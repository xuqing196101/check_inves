package yggc.model;

import java.util.Date;

public class SupplierExtracts {
    private Long id;

    private String projectName;

    private Long procurementDepId;

    private Date extractionTime;

    private String extractionSites;

    private Short extractTheWay;

    private String extractingConditions;

    private Long suppliersId;

    private Long extractsPeople;

    private Long superintendent;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getExtractingConditions() {
        return extractingConditions;
    }

    public void setExtractingConditions(String extractingConditions) {
        this.extractingConditions = extractingConditions == null ? null : extractingConditions.trim();
    }

    public Long getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(Long suppliersId) {
        this.suppliersId = suppliersId;
    }

    public Long getExtractsPeople() {
        return extractsPeople;
    }

    public void setExtractsPeople(Long extractsPeople) {
        this.extractsPeople = extractsPeople;
    }

    public Long getSuperintendent() {
        return superintendent;
    }

    public void setSuperintendent(Long superintendent) {
        this.superintendent = superintendent;
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