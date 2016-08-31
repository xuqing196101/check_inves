package yggc.model;

import java.util.Date;

public class SupplierBlackList {
    private Long id;

    private Long suppliersId;

    private Date startTime;

    private Date endTime;

    private Short punishType;

    private Short releaseType;

    private Short status;

    private String reason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuppliersId() {
        return suppliersId;
    }

    public void setSuppliersId(Long suppliersId) {
        this.suppliersId = suppliersId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Short getPunishType() {
        return punishType;
    }

    public void setPunishType(Short punishType) {
        this.punishType = punishType;
    }

    public Short getReleaseType() {
        return releaseType;
    }

    public void setReleaseType(Short releaseType) {
        this.releaseType = releaseType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
}