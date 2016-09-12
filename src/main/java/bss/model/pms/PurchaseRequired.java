package bss.model.pms;

import java.math.BigDecimal;
import java.util.Date;

public class PurchaseRequired {
	
    private String id;

    private String planName;

    private String planNo;

    private String planType;

    private String department;

    private String goodsName;

    private String stand;

    private String qualitStand;

    private String item;

    private Long purchaseCount;

    private BigDecimal price;

    private BigDecimal budget;

    private String deliverDate;

    private String purchaseType;

    private String supplier;

    private String isFreeTax;

    private String goodsUse;

    private String useUnit;

    private Date createdAt;

    private String userId;

    private String parentId;

    private String reqLevel;

    private String status;

    private String memo;

    private String seq;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand == null ? null : stand.trim();
    }

    public String getQualitStand() {
        return qualitStand;
    }

    public void setQualitStand(String qualitStand) {
        this.qualitStand = qualitStand == null ? null : qualitStand.trim();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    public Long getPurchaseNum() {
        return purchaseCount;
    }

    public void setPurchaseNum(Long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate == null ? null : deliverDate.trim();
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType == null ? null : purchaseType.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getIsFreeTax() {
        return isFreeTax;
    }

    public void setIsFreeTax(String isFreeTax) {
        this.isFreeTax = isFreeTax == null ? null : isFreeTax.trim();
    }

    public String getGoodsUse() {
        return goodsUse;
    }

    public void setGoodsUse(String goodsUse) {
        this.goodsUse = goodsUse == null ? null : goodsUse.trim();
    }

    public String getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(String useUnit) {
        this.useUnit = useUnit == null ? null : useUnit.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getComplieId() {
        return userId;
    }

    public void setComplieId(String complieId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(String reqLevel) {
        this.reqLevel = reqLevel == null ? null : reqLevel.trim();
    }

    public String getState() {
        return status;
    }

    public void setState(String state) {
        this.status = status == null ? null : status.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq == null ? null : seq.trim();
	}
    
    
}