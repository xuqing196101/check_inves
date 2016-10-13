package bss.model.ppms;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;

public class Project {
    private String id; //主键

    private String name; //

    private String projectNumber; //

    private Integer status; //

    private String principal; //

    private String ipone; // 

    private String linkman; //

    private String linkmanIpone; //

    private String bidUnit; //

    private String address; //

    private Integer postcode; //

    private Integer supplierNumber; //

    private Integer offerStandard; //

    private String prIntroduce; //

    private Integer budgetAmount; //

    private String passWord; //

    private String scoringRubric; //

    private String operator; //

    private String divisionOfWork; //

    private String purchaseType; //

    private String materialsType; //

    private String sectorOfDemand; //

    private PurchaseDep purchaseDep; //

    private Date deadline; //

    private Date dateOfEntrustment; //

    private Date bidDate; //

    private String bidAddress; //

    private String requieredId; //
    
    private String baleNo; //
    
    private double amount; //
    
    private String purchaseDepName; //
    
    private Integer executeStatus; //
    
    private Integer isEntrance; //
    
    private Supplier dealSupplier; //
    
    private Date approvalTime; //
    
    private Date replyTime; //
    
    private Date demandFromTime; //
    
    private Date taskGiveTime; //
    
    private Date createAt; //
    
    private Date startTime; //
    
    private Date noticeNewsTime; //
    
    private Date appTime; //
    
    private Date signUpTime; //
    
    private Date applyDeanline; //
    
    private Date noticeTime;
    
    private Date endTime;
    
    private Date signingTime;
    
    private Date acceptanceTime;
    
    private Date maintenanceTime;
    
    
    private List<Task> list;
    

	public Project(String id) {
		super();
		this.id = id;
	}
	
	public Project(){
		super();
	}
	
	public String getPurchaseDepName() {
		return purchaseDepName;
	}

	public void setPurchaseDepName(String purchaseDepName) {
		this.purchaseDepName = purchaseDepName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getIpone() {
		return ipone;
	}

	public void setIpone(String ipone) {
		this.ipone = ipone;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getLinkmanIpone() {
		return linkmanIpone;
	}

	public void setLinkmanIpone(String linkmanIpone) {
		this.linkmanIpone = linkmanIpone;
	}

	public String getBidUnit() {
		return bidUnit;
	}

	public void setBidUnit(String bidUnit) {
		this.bidUnit = bidUnit;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPostcode() {
		return postcode;
	}

	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}

	public Integer getSupplierNumber() {
		return supplierNumber;
	}

	public void setSupplierNumber(Integer supplierNumber) {
		this.supplierNumber = supplierNumber;
	}

	public Integer getOfferStandard() {
		return offerStandard;
	}

	public void setOfferStandard(Integer offerStandard) {
		this.offerStandard = offerStandard;
	}

	public Integer getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(Integer budgetAmount) {
		this.budgetAmount = budgetAmount;
	}

	public String getPrIntroduce() {
		return prIntroduce;
	}

	public void setPrIntroduce(String prIntroduce) {
		this.prIntroduce = prIntroduce;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getScoringRubric() {
		return scoringRubric;
	}

	public void setScoringRubric(String scoringRubric) {
		this.scoringRubric = scoringRubric;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getDivisionOfWork() {
		return divisionOfWork;
	}

	public void setDivisionOfWork(String divisionOfWork) {
		this.divisionOfWork = divisionOfWork;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getMaterialsType() {
		return materialsType;
	}

	public void setMaterialsType(String materialsType) {
		this.materialsType = materialsType;
	}

	public String getSectorOfDemand() {
		return sectorOfDemand;
	}

	public void setSectorOfDemand(String sectorOfDemand) {
		this.sectorOfDemand = sectorOfDemand;
	}

	public PurchaseDep getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(PurchaseDep purchaseDep) {
		this.purchaseDep = purchaseDep;
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
		this.bidAddress = bidAddress;
	}

	public String getRequieredId() {
		return requieredId;
	}

	public void setRequieredId(String requieredId) {
		this.requieredId = requieredId;
	}

	public String getBaleNo() {
		return baleNo;
	}

	public void setBaleNo(String baleNo) {
		this.baleNo = baleNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Supplier getDealSupplier() {
		return dealSupplier;
	}

	public void setDealSupplier(Supplier dealSupplier) {
		this.dealSupplier = dealSupplier;
	}

	public List<Task> getList() {
		return list;
	}

	public void setList(List<Task> list) {
		this.list = list;
	}

	public Integer getExecuteStatus() {
		return executeStatus;
	}

	public void setExecuteStatus(Integer executeStatus) {
		this.executeStatus = executeStatus;
	}

	public Integer getIsEntrance() {
		return isEntrance;
	}

	public void setIsEntrance(Integer isEntrance) {
		this.isEntrance = isEntrance;
	}

	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public Date getDemandFromTime() {
		return demandFromTime;
	}

	public void setDemandFromTime(Date demandFromTime) {
		this.demandFromTime = demandFromTime;
	}

	public Date getTaskGiveTime() {
		return taskGiveTime;
	}

	public void setTaskGiveTime(Date taskGiveTime) {
		this.taskGiveTime = taskGiveTime;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getNoticeNewsTime() {
		return noticeNewsTime;
	}

	public void setNoticeNewsTime(Date noticeNewsTime) {
		this.noticeNewsTime = noticeNewsTime;
	}

	public Date getAppTime() {
		return appTime;
	}

	public void setAppTime(Date appTime) {
		this.appTime = appTime;
	}

	public Date getSignUpTime() {
		return signUpTime;
	}

	public void setSignUpTime(Date signUpTime) {
		this.signUpTime = signUpTime;
	}

	public Date getApplyDeanline() {
		return applyDeanline;
	}

	public void setApplyDeanline(Date applyDeanline) {
		this.applyDeanline = applyDeanline;
	}

	public Date getNoticeTime() {
		return noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getSigningTime() {
		return signingTime;
	}

	public void setSigningTime(Date signingTime) {
		this.signingTime = signingTime;
	}

	public Date getAcceptanceTime() {
		return acceptanceTime;
	}

	public void setAcceptanceTime(Date acceptanceTime) {
		this.acceptanceTime = acceptanceTime;
	}

	public Date getMaintenanceTime() {
		return maintenanceTime;
	}

	public void setMaintenanceTime(Date maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}

	
}