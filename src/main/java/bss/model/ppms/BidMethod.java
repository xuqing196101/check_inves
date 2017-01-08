package bss.model.ppms;

import java.math.BigDecimal;

public class BidMethod {
	private String id ;//
	private String name ;//
	private String typeName ;//
	private String maxScore ;//
	private String remainScore ;//
	private String floatingRatio ;//下浮比例
	private String remark ;//
	private String isDeleted ;//
	private String createdAt ;//
	private String updatedAt ;//
	private String projectId ;//
	private BigDecimal valid;
	private BigDecimal business;
	private String packageId ;//
	private String markTermId;//瞬态属性  评分项id
	private String type;
	
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BigDecimal getValid() {
        return valid;
    }
    public void setValid(BigDecimal valid) {
        this.valid = valid;
    }
    public BigDecimal getBusiness() {
        return business;
    }
    public void setBusiness(BigDecimal business) {
        this.business = business;
    }
    public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getFloatingRatio() {
		return floatingRatio;
	}
	public void setFloatingRatio(String floatingRatio) {
		this.floatingRatio = floatingRatio;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getMarkTermId() {
		return markTermId;
	}
	public void setMarkTermId(String markTermId) {
		this.markTermId = markTermId;
	}
	public String getRemainScore() {
		return remainScore;
	}
	public void setRemainScore(String remainScore) {
		this.remainScore = remainScore;
	}
	
}
