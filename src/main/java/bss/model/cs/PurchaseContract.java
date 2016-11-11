package bss.model.cs;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PurchaseContract {
	private String id;
	
	private String code;//合同编号

	private String name;//合同名称

	private BigDecimal money;//合同金额

	private String demandSector;//需求部门

	private BigDecimal budget;//预算

	private BigDecimal year;//年度

	private String budgetSubjectItem;//项级预算科目

	private String approvalNumber;//合同批准问号

	private String documentNumber;//计划任务问号

	private String quaCode;//采购机构资格证号
	
	private Integer status;//状态

	private String purchaseDepName;//甲方单位

	private String purchaseLegal;//甲方法人

	private String purchaseAgent;//甲方委托代理人

	private String purchaseContact;//甲方联系人

	private String purchaseContactTelephone;//甲方联系电话

	private String purchaseContactAddress;//甲方联系地址

	private String purchaseUnitpostCode;//甲方邮编

	private String purchasePayDep;//甲方付款单位

	private String purchaseBank;//甲方开户银行

	private BigDecimal purchaseBankAccount;//甲方银行账号

	private String supplierDepName;//乙方单位

	private String supplierLegal;//乙方法人

	private String supplierAgent;//乙方委托代理人

	private String supplierContact;//乙方联系人

	private String supplierContactTelephone;//乙方联系电话

	private String supplierContactAddress;//乙方联系地址

	private String supplierUnitpostCode;//乙方邮编

	private String supplierBank;//乙方开户银行

	private BigDecimal supplierBankAccount;//乙方银行账号

	private String supplierBankName;//乙方开户名称

	private List<ContractRequired> contractReList;//合同明细list

	private String supplierPurId;//供应商机构组织代码

	private String content;//合同内容

	private String projectName;//项目名称

	private Integer isAppeal;//是否审价

	private String approvePic;//文件电子扫描件

	private Date createdAt;//创建时间

	private Date updatedAt;//修改时间

	private Integer isDeleted;//是否删除

	private Integer contractType;//合同类型

	private Date draftGitAt;//合同草稿提交时间

	private Date draftReviewedAt;//合同草稿报批时间

	private Date formalGitAt;//正式合同提交时间

	private Date formalReviewedAt;//正式合同报批时间

	/** 是否进口 0否 1是 */
	private Integer isImport;

	/** 是否申报 0待报 1已报 2已批准 */
	private Integer isDeclare = 0;

	private String purchaseType;//采购类型

	private String projectId;//项目id

	private BigDecimal finallyClosed;//最终结算金额

	public BigDecimal getFinallyClosed() {
		return finallyClosed;
	}

	public void setFinallyClosed(BigDecimal finallyClosed) {
		this.finallyClosed = finallyClosed;
	}

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