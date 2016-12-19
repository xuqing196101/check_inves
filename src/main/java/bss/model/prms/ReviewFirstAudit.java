package bss.model.prms;

public class ReviewFirstAudit {
    private String projectId;

    private String firstAuditId;

    private String supplierId;

    private Short isPass;

    private String packageId;
    
    private String rejectReason;
    
    private String expertId;
    
    private Integer isBack;//是否退回 0 ：否 1：退回
    

    public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

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

    public Integer getIsBack() {
      return isBack;
    }

    public void setIsBack(Integer isBack) {
      this.isBack = isBack;
    }
    
}