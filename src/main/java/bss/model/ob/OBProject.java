package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;
/**

* @Title:BidAnnouncementAttach 
* @Description: 存储竞价信息的内容
* @author 注释--Ma Mingwei
 */
public class OBProject {
    private String id;
    /**
     * @Fields name : 竞价名称
     */
    private String name;
    /**
     * @Fields deliveryDeadline : 交货截止时间
     */
    private Date deliveryDeadline;
    /**
     * @Fields deliveryAddress : 交货地点
     */
    private String deliveryAddress;
    /**
     * @Fields tradedSupplierCount : 成交供应商数
     */
    private Integer tradedSupplierCount;
    /**
     * @Fields transportFees : 运杂费
     */
    private BigDecimal transportFees;
    /**
     * @Fields demandUnit : 需求单位
     */
    private String demandUnit;
    /**
     * @Fields contactName : 联系人
     */
    private String contactName;
    /**
     * @Fields contactTel : 联系电话
     */
    private String contactTel;
    /**
     * @Fields orgId : 采购机构
     */
    private String orgId;
    /**
     * @Fields orgContactName : 采购联系人
     */
    private String orgContactName;
    /**
     * @Fields orgContactTel : 采购联系电话
     */
    private String orgContactTel;
    /**
     * @Fields startTime : 竞价开始时间
     */
    private Date startTime;
    /**
     * @Fields endTime : 竞价结束时间
     */
    private Date endTime;
    /**
     * @Fields content : 竞价内容
     */
    private String content	;
    /**
     * @Fields attachmentId : 附件
     */
    private String attachmentId;
    /**
     * @Fields remark : 备注
     */
    private String remark;
    /**
     * @Fields status : 发布状态
     */
    private Integer status;
    /**
     * @Fields createrId : 创建人ID
     */
    private String createrId;
    /**
     * @Fields formId : 来源竞价ID
     */
    private String formId;
    /**
     * @Fields turnoverRation : 成交比例
     */
    private Integer turnoverRation;
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;
    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;

    
    
    public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgContactName() {
		return orgContactName;
	}

	public void setOrgContactName(String orgContactName) {
		this.orgContactName = orgContactName;
	}

	public String getOrgContactTel() {
		return orgContactTel;
	}

	public void setOrgContactTel(String orgContactTel) {
		this.orgContactTel = orgContactTel;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getDeliveryDeadline() {
        return deliveryDeadline;
    }

    public void setDeliveryDeadline(Date deliveryDeadline) {
        this.deliveryDeadline = deliveryDeadline;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    public Integer getTradedSupplierCount() {
        return tradedSupplierCount;
    }

    public void setTradedSupplierCount(Integer tradedSupplierCount) {
        this.tradedSupplierCount = tradedSupplierCount;
    }

    public BigDecimal getTransportFees() {
        return transportFees;
    }

    public void setTransportFees(BigDecimal transportFees) {
        this.transportFees = transportFees;
    }

    public String getDemandUnit() {
        return demandUnit;
    }

    public void setDemandUnit(String demandUnit) {
        this.demandUnit = demandUnit == null ? null : demandUnit.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
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

    public String getContent	() {
        return content	;
    }

    public void setContent	(String content	) {
        this.content	 = content	 == null ? null : content	.trim();
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId == null ? null : attachmentId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId == null ? null : formId.trim();
    }

    public Integer getTurnoverRation() {
        return turnoverRation;
    }

    public void setTurnoverRation(Integer turnoverRation) {
        this.turnoverRation = turnoverRation;
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