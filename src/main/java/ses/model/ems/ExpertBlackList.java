package ses.model.ems;

import java.util.Date;

public class ExpertBlackList {
    private String id;

    private String loginName;

    private String relName;

    private Short isSysBuild;

    private Date storageTime;

    private Date dateOfPunishment;

    private Short punishType;

    private String punishDate;

    private String reason;

    private Date createdAt;

    private Date updatedAt;

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

    public Short getIsSysBuild() {
        return isSysBuild;
    }

    public void setIsSysBuild(Short isSysBuild) {
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

    public Short getPunishType() {
        return punishType;
    }

    public void setPunishType(Short punishType) {
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
}