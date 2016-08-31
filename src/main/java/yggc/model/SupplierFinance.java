package yggc.model;

import java.util.Date;

public class SupplierFinance {
    private Long id;

    private String name;

    private Short year;

    private Long mobile;

    private String auditors;

    private String index;

    private Long totalAssets;

    private Long totalLiabilities;

    private Long totalNetAssets;

    private Long taking;

    private String auditOpinion;

    private String liabilitiesList;

    private String profitList;

    private String cashFlowStatement;

    private String changeList;

    private Long supplierId;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public String getAuditors() {
        return auditors;
    }

    public void setAuditors(String auditors) {
        this.auditors = auditors == null ? null : auditors.trim();
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index == null ? null : index.trim();
    }

    public Long getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    public Long getTotalLiabilities() {
        return totalLiabilities;
    }

    public void setTotalLiabilities(Long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    public Long getTotalNetAssets() {
        return totalNetAssets;
    }

    public void setTotalNetAssets(Long totalNetAssets) {
        this.totalNetAssets = totalNetAssets;
    }

    public Long getTaking() {
        return taking;
    }

    public void setTaking(Long taking) {
        this.taking = taking;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public String getLiabilitiesList() {
        return liabilitiesList;
    }

    public void setLiabilitiesList(String liabilitiesList) {
        this.liabilitiesList = liabilitiesList == null ? null : liabilitiesList.trim();
    }

    public String getProfitList() {
        return profitList;
    }

    public void setProfitList(String profitList) {
        this.profitList = profitList == null ? null : profitList.trim();
    }

    public String getCashFlowStatement() {
        return cashFlowStatement;
    }

    public void setCashFlowStatement(String cashFlowStatement) {
        this.cashFlowStatement = cashFlowStatement == null ? null : cashFlowStatement.trim();
    }

    public String getChangeList() {
        return changeList;
    }

    public void setChangeList(String changeList) {
        this.changeList = changeList == null ? null : changeList.trim();
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
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