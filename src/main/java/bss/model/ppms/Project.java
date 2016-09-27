package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import bss.model.pms.PurchaseRequired;

import ses.model.oms.PurchaseDep;

public class Project {
    private String id;

    private String name;

    private String projectNumber;

    private Short status;

    private String principal;

    private String ipone;

    private String linkman;

    private String linkmanIpone;

    private String bidUnit;

    private String address;

    private BigDecimal postcode;

    private BigDecimal supplierNumber;

    private BigDecimal offerStandard;

    private String prIntroduce;

    private BigDecimal budgetAmount;

    private String passWord;

    private String scoringRubric;

    private String operator;

    private String divisionOfWork;

    private String purchaseType;

    private String materialsType;

    private String sectorOfDemand;

    private PurchaseDep purchaseDep;

    private Date deadline;

    private Date dateOfEntrustment;

    private Date bidDate;

    private String bidAddress;

    private List<PurchaseRequired> requieredList;

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

    public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal == null ? null : principal.trim();
    }

    public String getIpone() {
        return ipone;
    }

    public void setIpone(String ipone) {
        this.ipone = ipone == null ? null : ipone.trim();
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman == null ? null : linkman.trim();
    }

    public String getLinkmanIpone() {
        return linkmanIpone;
    }

    public void setLinkmanIpone(String linkmanIpone) {
        this.linkmanIpone = linkmanIpone == null ? null : linkmanIpone.trim();
    }

    public String getBidUnit() {
        return bidUnit;
    }

    public void setBidUnit(String bidUnit) {
        this.bidUnit = bidUnit == null ? null : bidUnit.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getPostcode() {
        return postcode;
    }

    public void setPostcode(BigDecimal postcode) {
        this.postcode = postcode;
    }

    public BigDecimal getSupplierNumber() {
        return supplierNumber;
    }

    public void setSupplierNumber(BigDecimal supplierNumber) {
        this.supplierNumber = supplierNumber;
    }

    public BigDecimal getOfferStandard() {
        return offerStandard;
    }

    public void setOfferStandard(BigDecimal offerStandard) {
        this.offerStandard = offerStandard;
    }

    public String getPrIntroduce() {
        return prIntroduce;
    }

    public void setPrIntroduce(String prIntroduce) {
        this.prIntroduce = prIntroduce == null ? null : prIntroduce.trim();
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getScoringRubric() {
        return scoringRubric;
    }

    public void setScoringRubric(String scoringRubric) {
        this.scoringRubric = scoringRubric == null ? null : scoringRubric.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getDivisionOfWork() {
        return divisionOfWork;
    }

    public void setDivisionOfWork(String divisionOfWork) {
        this.divisionOfWork = divisionOfWork == null ? null : divisionOfWork.trim();
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType == null ? null : purchaseType.trim();
    }

    public String getMaterialsType() {
        return materialsType;
    }

    public void setMaterialsType(String materialsType) {
        this.materialsType = materialsType == null ? null : materialsType.trim();
    }

    public String getSectorOfDemand() {
        return sectorOfDemand;
    }

    public void setSectorOfDemand(String sectorOfDemand) {
        this.sectorOfDemand = sectorOfDemand == null ? null : sectorOfDemand.trim();
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDateOfEntrustment() {
        return dateOfEntrustment;
    }

    public void setDateOfEntrustment(Date dateOfEntrustment) {
        this.dateOfEntrustment = dateOfEntrustment;
    }

    public Date getBidDate() {
        return bidDate;
    }

    public void setBidDate(Date bidDate) {
        this.bidDate = bidDate;
    }

    public String getBidAddress() {
        return bidAddress;
    }

    public void setBidAddress(String bidAddress) {
        this.bidAddress = bidAddress == null ? null : bidAddress.trim();
    }

	public PurchaseDep getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(PurchaseDep purchaseDep) {
		this.purchaseDep = purchaseDep;
	}

	public List<PurchaseRequired> getRequieredList() {
		return requieredList;
	}

	public void setRequieredList(List<PurchaseRequired> requieredList) {
		this.requieredList = requieredList;
	}
}