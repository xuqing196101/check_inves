package yggc.model.ems;

import java.util.Date;

public class ExpertHonesty {
    private String id;

    private Long expertId;

    private String expertName;

    private String badBehavior;

    private Date createdAt;

    private Date updatedAt;

    private Date inStorageAt;

    private String expertNature;

    private String expertCall;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName == null ? null : expertName.trim();
    }

    public String getBadBehavior() {
        return badBehavior;
    }

    public void setBadBehavior(String badBehavior) {
        this.badBehavior = badBehavior == null ? null : badBehavior.trim();
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

    public Date getInStorageAt() {
        return inStorageAt;
    }

    public void setInStorageAt(Date inStorageAt) {
        this.inStorageAt = inStorageAt;
    }

    public String getExpertNature() {
        return expertNature;
    }

    public void setExpertNature(String expertNature) {
        this.expertNature = expertNature == null ? null : expertNature.trim();
    }

    public String getExpertCall() {
        return expertCall;
    }

    public void setExpertCall(String expertCall) {
        this.expertCall = expertCall == null ? null : expertCall.trim();
    }
}