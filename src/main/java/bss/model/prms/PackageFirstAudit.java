package bss.model.prms;

public class PackageFirstAudit {
    private String packageId;

    private String firstAuditId;

    private String projectId;

    private Short isConfirm;
    
    /**
     * @Fields firstAuditName : 初审项名称，不存储数据库
     */
    private String firstAuditName;
    
    /**
     * @Fields firstAuditKind : 初审项类型，不存储数据库
     */
    private String firstAuditKind;
    
    /**
     * @Fields is_pass : 初审项是否合格，不存储数据库
     */
    private Integer is_pass;

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

    public String getFirstAuditName() {
        return firstAuditName;
    }

    public void setFirstAuditName(String firstAuditName) {
        this.firstAuditName = firstAuditName;
    }

    public String getFirstAuditKind() {
        return firstAuditKind;
    }

    public void setFirstAuditKind(String firstAuditKind) {
        this.firstAuditKind = firstAuditKind;
    }

    public Integer getIs_pass() {
        return is_pass;
    }

    public void setIs_pass(Integer is_pass) {
        this.is_pass = is_pass;
    }
    
}