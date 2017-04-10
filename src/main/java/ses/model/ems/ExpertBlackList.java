package ses.model.ems;

import java.util.Date;

public class ExpertBlackList {
    private String id; //主键

    private String loginName; //专家登录名

    private String relName; //专家名称

    private Integer isSysBuild; //是否系统内注册专家

    private Date storageTime; //入库时间

    private Date dateOfPunishment; //处罚日期

    private Integer punishType; //处罚类型

    /**三个月  六个月  一年  两年  三年**/
    private String punishDate; //处罚时限

    private String reason; //理由

    private Date createdAt; //创建时间

    private Date updatedAt; //更新时间

    private String attachmentCert; //附件
    
    private Integer status; //状态（0是处罚中，1过期，2手动移除）
    
    private String expertId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName == null ? null : relName.trim();
    }

    public Integer getIsSysBuild() {
        return isSysBuild;
    }

    public void setIsSysBuild(Integer isSysBuild) {
        this.isSysBuild = isSysBuild;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public Date getDateOfPunishment() {
        return dateOfPunishment;
    }

    public void setDateOfPunishment(Date dateOfPunishment) {
        this.dateOfPunishment = dateOfPunishment;
    }

    public Integer getPunishType() {
        return punishType;
    }

    public void setPunishType(Integer punishType) {
        this.punishType = punishType;
    }

    public String getPunishDate() {
        return punishDate;
    }

    public void setPunishDate(String punishDate) {
        this.punishDate = punishDate == null ? null : punishDate.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAttachmentCert() {
        return attachmentCert;
    }

    public void setAttachmentCert(String attachmentCert) {
        this.attachmentCert = attachmentCert == null ? null : attachmentCert.trim();
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getExpertId() {
		return expertId;
	}

	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}


    
}