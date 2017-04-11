package bss.model.ob;

import java.util.Date;
/**
 * 
* @ClassName: OBProjectRule 
* @Description: 竞价规则表子表POJO
* @author Easong
* @date 2017年3月29日 上午10:13:47 
*
 */
public class OBProjectRule {
	/**
	 * 竞价项目ID
	 */
    private String id;

    /**
     * 间隔工作日(天)
     */
    private Integer intervalWorkday;

    /**
     * 具体时间点
     */
    private Date definiteTime;

    /**
     * 报价时间（分钟）
     */
    private Integer quoteTime;

    /**
     * 确认时间（分钟）
     */
    private Integer confirmTime;

    /**
     * 默认标识
     */
    private Integer status;

    /**
     * 创建人ID
     */
    private String createrId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 项目数量竞标数量累加
     */
    private Integer bidingCount;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 竞价规则名称
     */
    private String name;

    /**
     * 第二次确认时间(分钟)
     */
    private Integer confirmTimeSecond;

    /**
     * 最少供应商数量
     */
    private Integer leastSupplierNum;
    
    
    /**二次报价时间**/
    private Integer quoteTimeSecond;
    /**有效百分比**/
    private Integer percent;
    /**浮动比例**/
    private Integer floatPercent;
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

	public Integer getQuoteTimeSecond() {
		return quoteTimeSecond;
	}

	public void setQuoteTimeSecond(Integer quoteTimeSecond) {
		this.quoteTimeSecond = quoteTimeSecond;
	}

	public Integer getPercent() {
		return percent;
	}

	public void setPercent(Integer percent) {
		this.percent = percent;
	}

	public Integer getFloatPercent() {
		return floatPercent;
	}

	public void setFloatPercent(Integer floatPercent) {
		this.floatPercent = floatPercent;
	}
    
    
}