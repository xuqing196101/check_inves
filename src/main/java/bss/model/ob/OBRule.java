package bss.model.ob;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class OBRule {
    private String id;
    /**间隔工作日**/
    private Integer intervalWorkday;
    /**竞价开始时间**/

    @DateTimeFormat(pattern="HH:mm:ss")
    private Date definiteTime;
    
    /**报价时间**/
    private Integer quoteTime;
    /**确认时间（第一轮）**/
    private Integer confirmTime;

    /**1:默认  0:否**/
    private Integer status;

    private String createrId;

    private String remark;
    /**项目数量**/
    private Integer bidingCount;

    private Date createdAt;

    private Date updatedAt;
    
    /**竞价规则名称**/
    private String name;

    /**确认时间（第二轮）**/
    private Integer confirmTimeSecond;

    /**最少供应商数量**/
    private Integer leastSupplierNum;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getConfirmTimeSecond() {
        return confirmTimeSecond;
    }

    public void setConfirmTimeSecond(Integer confirmTimeSecond) {
        this.confirmTimeSecond = confirmTimeSecond;
    }

	public Integer getLeastSupplierNum() {
		return leastSupplierNum;
	}

	public void setLeastSupplierNum(Integer leastSupplierNum) {
		this.leastSupplierNum = leastSupplierNum;
	}
    
    
}