package bss.model.ob;

import java.util.Date;

public class OBRule {
    private String id;

    private Integer intervalWorkday;

    private Date definiteTime;

    private Integer quoteTime;

    private Integer confirmTime;

    private Integer status;

    private String createrId;

    private String remark;

    private Integer bidingCount;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getIntervalWorkday() {
        return intervalWorkday;
    }

    public void setIntervalWorkday(Integer intervalWorkday) {
        this.intervalWorkday = intervalWorkday;
    }

    public Date getDefiniteTime() {
        return definiteTime;
    }

    public void setDefiniteTime(Date definiteTime) {
        this.definiteTime = definiteTime;
    }

    public Integer getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Integer quoteTime) {
        this.quoteTime = quoteTime;
    }

    public Integer getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Integer confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId == null ? null : createrId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getBidingCount() {
        return bidingCount;
    }

    public void setBidingCount(Integer bidingCount) {
        this.bidingCount = bidingCount;
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