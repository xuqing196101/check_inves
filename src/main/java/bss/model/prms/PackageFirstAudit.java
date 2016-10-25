package bss.model.prms;

public class PackageFirstAudit {
    private String packageId;

    private String firstAuditId;

    private String projectId;

    private Short isConfirm;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getFirstAuditId() {
        return firstAuditId;
    }

    public void setFirstAuditId(String firstAuditId) {
        this.firstAuditId = firstAuditId == null ? null : firstAuditId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Short getIsConfirm() {
        return isConfirm;
    }

    public void setIsConfirm(Short isConfirm) {
        this.isConfirm = isConfirm;
    }
}