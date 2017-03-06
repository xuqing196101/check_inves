package bss.model.ob;

import java.math.BigDecimal;
import java.util.Date;

public class OBProject {
    private String id;

    private String name;

    private Date deliveryDeadline;

    private String deliveryAddress;

    private Integer tradedSupplierCount;

    private BigDecimal transportFees;

    private String demandUnit;

    private String contactName;

    private Date startTime;

    private Date endTime;

    private String content	;

    private String attachmentId;

    private String remark;

    private Integer status;

    private String createrId;

    private String formId;

    private Integer turnoverRation;

    private Date createdAt;

    private Date updatedAt;

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