package bss.model.prms;

public class PackageExpert {
    private String packageId;

    private String expertId;

    private String projectId;

    private Short isGroupLeader;

    private Short isAudit;
    
    private Short isGather;

    
    

	public Short getIsGather() {
		return isGather;
	}

	public void setIsGather(Short isGather) {
		this.isGather = isGather;
	}

	public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public Short getIsGroupLeader() {
        return isGroupLeader;
    }

    public void setIsGroupLeader(Short isGroupLeader) {
        this.isGroupLeader = isGroupLeader;
    }

    public Short getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Short isAudit) {
        this.isAudit = isAudit;
    }
}