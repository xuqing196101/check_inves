package bss.model.prms;

public class ReviewProgress {
    private String id;
    //项目id
    private String projectId;
    //包名称
    private String packageName;
    //包id
    private String packageId;
    //评审状态
    private String auditStatus;
    //总进度
    private Double totalProgress;
    //初审进度
    private Double firstAuditProgress;
    //评分进度
    private Double scoreProgress;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName == null ? null : packageName.trim();
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

	public Double getTotalProgress() {
		return totalProgress;
	}

	public void setTotalProgress(Double totalProgress) {
		this.totalProgress = totalProgress;
	}

	public Double getFirstAuditProgress() {
		return firstAuditProgress;
	}

	public void setFirstAuditProgress(Double firstAuditProgress) {
		this.firstAuditProgress = firstAuditProgress;
	}

	public Double getScoreProgress() {
		return scoreProgress;
	}

	public void setScoreProgress(Double scoreProgress) {
		this.scoreProgress = scoreProgress;
	}

   
}