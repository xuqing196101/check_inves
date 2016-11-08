package bss.model.cs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class PurchaseContract {
    private String id;
    
    @NotBlank(message = "合同编号不能为空")
    private String code;
    
    @NotBlank(message = "合同名称不能为空")
    private String name;
    
    private BigDecimal money;
    
    @NotBlank(message = "需求部门不能为空")
    private String demandSector;
    
    private BigDecimal budget;
    
    private BigDecimal year;
    
    @NotBlank(message = "项级预算科目不能为空")
    private String budgetSubjectItem;
    
    private String approvalNumber;
    
    @NotBlank(message = "计划任务文号不能为空")
    private String documentNumber;
    
    @NotBlank(message = "采购机构文号不能为空")
    private String quaCode;
    
    private Integer status;
    
    @NotBlank(message = "甲方单位不能为空")
    private String purchaseDepName;
    
    @NotBlank(message = "甲方法人不能为空")
    private String purchaseLegal;
    
    @NotBlank(message = "甲方委托代理人不能为空")
    private String purchaseAgent;
    
    @NotBlank(message = "甲方联系人不能为空")
    private String purchaseContact;
    
    @NotBlank(message = "甲方联系电话不能为空")
    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$",message = "请输入正确的联系电话")
    private String purchaseContactTelephone;
    
    @NotBlank(message = "甲方地址不能为空")
    private String purchaseContactAddress;
    
    @NotBlank(message = "甲方邮编不能为空")
    @Pattern(regexp = "[1-9]\\d{5}(?!\\d)",message = "请输入正确的邮编")
    private String purchaseUnitpostCode;
    
    @NotBlank(message = "甲方付款单位不能为空")
    private String purchasePayDep;
    
    @NotBlank(message = "甲方开户银行不能为空")
    private String purchaseBank;
    
    private BigDecimal purchaseBankAccount;
    
    @NotBlank(message = "乙方单位不能为空")
    private String supplierDepName;
    
    @NotBlank(message = "乙方法人不能为空")
    private String supplierLegal;
    
    @NotBlank(message = "乙方委托代理人不能为空")
    private String supplierAgent;
    
    @NotBlank(message = "乙方联系人不能为空")
    private String supplierContact;
    
    @NotBlank(message = "乙方联系电话不能为空")
    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[3584]\\d{9}))$",message = "请输入正确的联系电话")
    private String supplierContactTelephone;

    @NotBlank(message = "乙方地址不能为空")
    private String supplierContactAddress;
    
    @NotBlank(message = "乙方邮编不能为空")
    @Pattern(regexp = "[1-9]\\d{5}(?!\\d)",message = "请输入正确的邮编")
    private String supplierUnitpostCode;
    
    @NotBlank(message = "乙方开户银行不能为空")
    private String supplierBank;
    
    private BigDecimal supplierBankAccount;
    
    @NotBlank(message = "乙方开户名称不能为空")
    private String supplierBankName;
    
    private List<ContractRequired> contractReList;
    
    private String supplierPurId;
    
    @NotBlank(message = "合同正文不能为空")
    private String content;
    
    private String projectName;
    
    private Integer isAppeal;
    
    private String approvePic;
    
    private Date createdAt;
    
    private Date updatedAt;
    
    private Integer isDeleted;
    
    private Integer contractType;
    
    private Date draftGitAt;
    
    private Date draftReviewedAt;
    
    private Date formalGitAt;
    
    private Date formalReviewedAt;
    
    /** 是否进口 0否 1是 */
	private Integer isImport;
	
	/** 是否申报 0待报 1已报 2已批准 */
	private Integer isDeclare = 0;
	
	private String purchaseType;
	
	private String projectId;

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getId() {
		return id;
	}
	
    public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getDemandSector() {
		return demandSector;
	}

	public void setDemandSector(String demandSector) {
		this.demandSector = demandSector;
	}

	public BigDecimal getBudget() {
		return budget;
	}

	public void setBudget(BigDecimal budget) {
		this.budget = budget;
	}

	public BigDecimal getYear() {
		return year;
	}

	public void setYear(BigDecimal year) {
		this.year = year;
	}

	public String getBudgetSubjectItem() {
		return budgetSubjectItem;
	}

	public void setBudgetSubjectItem(String budgetSubjectItem) {
		this.budgetSubjectItem = budgetSubjectItem;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getQuaCode() {
		return quaCode;
	}

	public void setQuaCode(String quaCode) {
		this.quaCode = quaCode;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPurchaseDepName() {
		return purchaseDepName;
	}

	public void setPurchaseDepName(String purchaseDepName) {
		this.purchaseDepName = purchaseDepName;
	}

	public String getPurchaseLegal() {
		return purchaseLegal;
	}

	public void setPurchaseLegal(String purchaseLegal) {
		this.purchaseLegal = purchaseLegal;
	}

	public String getPurchaseAgent() {
		return purchaseAgent;
	}

	public void setPurchaseAgent(String purchaseAgent) {
		this.purchaseAgent = purchaseAgent;
	}

	public String getPurchaseContact() {
		return purchaseContact;
	}

	public void setPurchaseContact(String purchaseContact) {
		this.purchaseContact = purchaseContact;
	}

	public String getPurchaseContactTelephone() {
		return purchaseContactTelephone;
	}

	public void setPurchaseContactTelephone(String purchaseContactTelephone) {
		this.purchaseContactTelephone = purchaseContactTelephone;
	}

	public String getPurchaseContactAddress() {
		return purchaseContactAddress;
	}

	public void setPurchaseContactAddress(String purchaseContactAddress) {
		this.purchaseContactAddress = purchaseContactAddress;
	}

	public String getPurchaseUnitpostCode() {
		return purchaseUnitpostCode;
	}

	public void setPurchaseUnitpostCode(String purchaseUnitpostCode) {
		this.purchaseUnitpostCode = purchaseUnitpostCode;
	}

	public String getPurchasePayDep() {
		return purchasePayDep;
	}

	public void setPurchasePayDep(String purchasePayDep) {
		this.purchasePayDep = purchasePayDep;
	}

	public String getPurchaseBank() {
		return purchaseBank;
	}

	public void setPurchaseBank(String purchaseBank) {
		this.purchaseBank = purchaseBank;
	}

	public BigDecimal getPurchaseBankAccount() {
		return purchaseBankAccount;
	}

	public void setPurchaseBankAccount(BigDecimal purchaseBankAccount) {
		this.purchaseBankAccount = purchaseBankAccount;
	}

	public String getSupplierDepName() {
		return supplierDepName;
	}

	public void setSupplierDepName(String supplierDepName) {
		this.supplierDepName = supplierDepName;
	}

	public String getSupplierLegal() {
		return supplierLegal;
	}

	public void setSupplierLegal(String supplierLegal) {
		this.supplierLegal = supplierLegal;
	}

	public String getSupplierAgent() {
		return supplierAgent;
	}

	public void setSupplierAgent(String supplierAgent) {
		this.supplierAgent = supplierAgent;
	}

	public String getSupplierContact() {
		return supplierContact;
	}

	public void setSupplierContact(String supplierContact) {
		this.supplierContact = supplierContact;
	}

	public String getSupplierContactTelephone() {
		return supplierContactTelephone;
	}

	public void setSupplierContactTelephone(String supplierContactTelephone) {
		this.supplierContactTelephone = supplierContactTelephone;
	}

	public String getSupplierContactAddress() {
		return supplierContactAddress;
	}

	public void setSupplierContactAddress(String supplierContactAddress) {
		this.supplierContactAddress = supplierContactAddress;
	}

	public String getSupplierUnitpostCode() {
		return supplierUnitpostCode;
	}

	public void setSupplierUnitpostCode(String supplierUnitpostCode) {
		this.supplierUnitpostCode = supplierUnitpostCode;
	}

	public String getSupplierBank() {
		return supplierBank;
	}

	public void setSupplierBank(String supplierBank) {
		this.supplierBank = supplierBank;
	}

	public BigDecimal getSupplierBankAccount() {
		return supplierBankAccount;
	}

	public void setSupplierBankAccount(BigDecimal supplierBankAccount) {
		this.supplierBankAccount = supplierBankAccount;
	}

	public String getSupplierBankName() {
		return supplierBankName;
	}

	public void setSupplierBankName(String supplierBankName) {
		this.supplierBankName = supplierBankName;
	}

	public List<ContractRequired> getContractReList() {
		return contractReList;
	}

	public void setContractReList(List<ContractRequired> contractReList) {
		this.contractReList = contractReList;
	}

	public String getSupplierPurId() {
		return supplierPurId;
	}

	public void setSupplierPurId(String supplierPurId) {
		this.supplierPurId = supplierPurId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getIsAppeal() {
		return isAppeal;
	}

	public void setIsAppeal(Integer isAppeal) {
		this.isAppeal = isAppeal;
	}

	public String getApprovePic() {
		return approvePic;
	}

	public void setApprovePic(String approvePic) {
		this.approvePic = approvePic;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public Date getDraftGitAt() {
		return draftGitAt;
	}

	public void setDraftGitAt(Date draftGitAt) {
		this.draftGitAt = draftGitAt;
	}

	public Date getDraftReviewedAt() {
		return draftReviewedAt;
	}

	public void setDraftReviewedAt(Date draftReviewedAt) {
		this.draftReviewedAt = draftReviewedAt;
	}

	public Date getFormalGitAt() {
		return formalGitAt;
	}

	public void setFormalGitAt(Date formalGitAt) {
		this.formalGitAt = formalGitAt;
	}

	public Date getFormalReviewedAt() {
		return formalReviewedAt;
	}

	public void setFormalReviewedAt(Date formalReviewedAt) {
		this.formalReviewedAt = formalReviewedAt;
	}

	public Integer getIsImport() {
		return isImport;
	}

	public void setIsImport(Integer isImport) {
		this.isImport = isImport;
	}

	public Integer getIsDeclare() {
		return isDeclare;
	}

	public void setIsDeclare(Integer isDeclare) {
		this.isDeclare = isDeclare;
	}

}