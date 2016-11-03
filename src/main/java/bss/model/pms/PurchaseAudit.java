package bss.model.pms;

public class PurchaseAudit {
    private String purchaseId;

    private String auditParamId;

    private String paramValue;

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId == null ? null : purchaseId.trim();
    }

    public String getAuditParamId() {
        return auditParamId;
    }

    public void setAuditParamId(String auditParamId) {
        this.auditParamId = auditParamId == null ? null : auditParamId.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }
}