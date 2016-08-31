package yggc.model;

import java.util.Date;

public class SupplierMatSe {
    private Long id;

    private Long supplierId;

    private String orgName;

    private Long totalPerson;

    private Long totalMange;

    private Long totalTech;

    private Long totalWorker;

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