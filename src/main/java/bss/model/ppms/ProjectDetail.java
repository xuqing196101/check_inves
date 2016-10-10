package bss.model.ppms;

import java.util.Date;

import ses.model.oms.PurchaseDep;

public class ProjectDetail {
	private String id;

	private String serialNumber;

	private String department;

	private String goodsName;

	private String stand;

	private String qualitStand;

	private String item;

	private Integer purchaseCount;

	private Double price;

	private Double budget;

	private String deliverDate;

	private String purchaseType;

	private String supplier;

	private String isFreeTax;

	private String goodsUse;

	private String useUnit;

	private Project project;

	private String status;

	private PurchaseDep purchaseDep;
	
	private String packageId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getStand() {
		return stand;
	}

	public void setStand(String stand) {
		this.stand = stand;
	}

	public String getQualitStand() {
		return qualitStand;
	}

	public void setQualitStand(String qualitStand) {
		this.qualitStand = qualitStand;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getPurchaseCount() {
		return purchaseCount;
	}

	public void setPurchaseCount(Integer purchaseCount) {
		this.purchaseCount = purchaseCount;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public String getDeliverDate() {
		return deliverDate;
	}

	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getIsFreeTax() {
		return isFreeTax;
	}

	public void setIsFreeTax(String isFreeTax) {
		this.isFreeTax = isFreeTax;
	}

	public String getGoodsUse() {
		return goodsUse;
	}

	public void setGoodsUse(String goodsUse) {
		this.goodsUse = goodsUse;
	}

	public String getUseUnit() {
		return useUnit;
	}

	public void setUseUnit(String useUnit) {
		this.useUnit = useUnit;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PurchaseDep getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(PurchaseDep purchaseDep) {
		this.purchaseDep = purchaseDep;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	

	
}