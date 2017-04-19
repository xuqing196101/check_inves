package bss.model.ppms;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;

public class AdvancedProject {
 private String id; //主键
    
    @NotBlank(message="不能为空")
    private String name; //项目名称

    @NotBlank(message="不能为空")
    private String projectNumber; //项目编号

    private String status; //状态
    
    private String principal; //项目负责人

    private String ipone; // 项目负责人联系电话

    private String linkman; //项目联系人

    private String linkmanIpone; //联系人电话

    private String bidUnit; //

    private String address; //联系地址

    private Integer postcode; //邮编

    private Integer supplierNumber; //最少供应商人数

    private Integer offerStandard; //报价标准分值

    private String prIntroduce; //项目介绍

    private Integer budgetAmount; //预算金额（万元）

    private String passWord; //密码

    private String scoringRubric; //评分规则

    private String operator; //经办人

    private String divisionOfWork; //工作分工

    private String purchaseType; //采购方式：公开招标，邀请招标，询价，竞争性谈判，单一来源

    private String materialsType; //物资类别 

    private String sectorOfDemand; //需求部门

    private PurchaseDep purchaseDep; //采购机构
    
    private String purchaseDepId;

    private Date deadline; //投标截止时间

    private Date dateOfEntrustment; //委托日期

    private Date bidDate; //开标时间

    private String bidAddress; //开标地点

    private String requieredId; //
    
    private String baleNo; //包号
    
    private double amount; //成交金额
    
    private String purchaseDepName; //采购机构名称
    
    private Integer executeStatus; //执行状态
    
    private Integer isEntrance; //
    
    private Supplier dealSupplier; //成交供应商
    
    private Date approvalTime; //招标文件报批时间
    
    private Date replyTime; //招标文件批复时间
    
    private Date demandFromTime; //需求计划提报时间
    
    private Integer IsRehearse; //是否预研
    
    private Date createAt; //创建时间
    
    private Date startTime; //启动时间
    
    private Date noticeNewsTime; //招标公告发布时间
    
    private Date appTime; //招标公告审批时间
    
    private Date signUpTime; //供应商报名时间
    
    private Date applyDeanline; //报名截止时间
    
    private Date noticeTime; //发送中标通知书时间
    
    private Date endTime; //项目结束时间
    
    private Date signingTime; //合同签订时间
    
    private Date acceptanceTime; //验收时间
    
    private Date maintenanceTime; //售后维护时间
    
    private Integer isImport;
    
    private Integer isProvisional;
    
    private String planType; //（货物，工程，服务）
    
    private Integer confirmFile; //确认制作招标文件内容。0：未确认  ，1：已确认

    private List<AdvancedPackages> packagesList;
    
    private String projectContractor;//项目承办人
    
    private String auditReason;//招标文件审核原因
    
    private String[] statusArray;//状态集合
    
    private String appointMan; // 立项人
    
    
    public AdvancedProject(String id) {
        super();
        this.id = id;
    }
    
    public AdvancedProject(){
        super();
    }


    public List<AdvancedPackages> getPackagesList() {
        return packagesList;
    }

    public void setPackagesList(List<AdvancedPackages> packagesList) {
        this.packagesList = packagesList;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    
    public Integer getIsRehearse() {
        return IsRehearse;
    }

    public void setIsRehearse(Integer isRehearse) {
        IsRehearse = isRehearse;
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

    public Integer getIsImport() {
        return isImport;
    }

    public void setIsImport(Integer isImport) {
        this.isImport = isImport;
    }

    public Integer getIsProvisional() {
        return isProvisional;
    }

    public void setIsProvisional(Integer isProvisional) {
        this.isProvisional = isProvisional;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
    }

    public Integer getConfirmFile() {
        return confirmFile;
    }

    public void setConfirmFile(Integer confirmFile) {
        this.confirmFile = confirmFile;
    }

    public String getPurchaseDepId() {
        return purchaseDepId;
    }

    public void setPurchaseDepId(String purchaseDepId) {
        this.purchaseDepId = purchaseDepId;
    }

    public String getProjectContractor() {
        return projectContractor;
    }

    public void setProjectContractor(String projectContractor) {
        this.projectContractor = projectContractor;
    }

    public String getAuditReason() {
        return auditReason;
    }

    public void setAuditReason(String auditReason) {
        this.auditReason = auditReason;
    }

    public String[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(String[] statusArray) {
        this.statusArray = statusArray;
    }

    public String getAppointMan() {
        return appointMan;
    }

    public void setAppointMan(String appointMan) {
        this.appointMan = appointMan;
    }

}
