package ses.model.sms;

import java.sql.Timestamp;

public class ApplyEdit {
    private String id;

    private String supplierName;

    private String creditCode;

    private String updateBeforeSupplierInfo;

    private String updateAfterSupplierInfo;

    private String updateReason;

    private Timestamp createdAt;

    private String supplierId;

    private String orgName;

    private Short auditStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getUpdateBeforeSupplierInfo() {
        return updateBeforeSupplierInfo;
    }

    public void setUpdateBeforeSupplierInfo(String updateBeforeSupplierInfo) {
        this.updateBeforeSupplierInfo = updateBeforeSupplierInfo == null ? null : updateBeforeSupplierInfo.trim();
    }

    public String getUpdateAfterSupplierInfo() {
        return updateAfterSupplierInfo;
    }

    public void setUpdateAfterSupplierInfo(String updateAfterSupplierInfo) {
        this.updateAfterSupplierInfo = updateAfterSupplierInfo == null ? null : updateAfterSupplierInfo.trim();
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason == null ? null : updateReason.trim();
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Short getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Short auditStatus) {
        this.auditStatus = auditStatus;
    }
}