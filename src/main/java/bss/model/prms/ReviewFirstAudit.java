package bss.model.prms;

public class ReviewFirstAudit {
    private String projectId;

    private String firstAuditId;

    private String supplierId;

    private Short isPass;

    private String packageId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getFirstAuditId() {
        return firstAuditId;
    }

    public void setFirstAuditId(String firstAuditId) {
        this.firstAuditId = firstAuditId == null ? null : firstAuditId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public Short getIsPass() {
        return isPass;
    }

    public void setIsPass(Short isPass) {
        this.isPass = isPass;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }
}