package bss.model.prms;

public class PackageFirstAudit {
	/*包id*/
    private String packageId;
    /*初审项id*/
    private String firstAuditId;
    /*项目id*/
    private String projectId;

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
}