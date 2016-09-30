package bss.model.cs;

import java.math.BigDecimal;

public class PurchaseContract {
    private String id;

    private String code;

    private String name;

    private BigDecimal money;

    private String supplierId;

    private String demandSector;

    private String planFileName;

    private BigDecimal budget;

    private Short year;

    private String budgetSubjectItem;

    private String approvalNumber;

    private String documentNumber;

    private String quaCode;

    private Short status;

    private String contractRequiredId;

    private String purchaseDepName;

    private String purchaseLegal;

    private String purchaseAgent;

    private String purchaseContact;

    private String purchaseContactTelephone;

    private String purchaseContactAddress;

    private String purchaseUnitpostCode;

    private String purchasePayDep;

    private String purchaseBank;

    private BigDecimal purchaseBankAccount;

    private String supplierDepName;

    private String supplierLegal;

    private String supplierAgent;

    private String supplierContact;

    private String suopplierContactTelephone;

    private String supplierContactAddress;

    private String supplierUnitpostCode;

    private String supplierBank;

    private BigDecimal supplierBankAccount;

    private String supplierBankName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getDemandSector() {
        return demandSector;
    }

    public void setDemandSector(String demandSector) {
        this.demandSector = demandSector == null ? null : demandSector.trim();
    }

    public String getPlanFileName() {
        return planFileName;
    }

    public void setPlanFileName(String planFileName) {
        this.planFileName = planFileName == null ? null : planFileName.trim();
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getBudgetSubjectItem() {
        return budgetSubjectItem;
    }

    public void setBudgetSubjectItem(String budgetSubjectItem) {
        this.budgetSubjectItem = budgetSubjectItem == null ? null : budgetSubjectItem.trim();
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber == null ? null : approvalNumber.trim();
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber == null ? null : documentNumber.trim();
    }

    public String getQuaCode() {
        return quaCode;
    }

    public void setQuaCode(String quaCode) {
        this.quaCode = quaCode == null ? null : quaCode.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getContractRequiredId() {
        return contractRequiredId;
    }

    public void setContractRequiredId(String contractRequiredId) {
        this.contractRequiredId = contractRequiredId == null ? null : contractRequiredId.trim();
    }

    public String getPurchaseDepName() {
        return purchaseDepName;
    }

    public void setPurchaseDepName(String purchaseDepName) {
        this.purchaseDepName = purchaseDepName == null ? null : purchaseDepName.trim();
    }

    public String getPurchaseLegal() {
        return purchaseLegal;
    }

    public void setPurchaseLegal(String purchaseLegal) {
        this.purchaseLegal = purchaseLegal == null ? null : purchaseLegal.trim();
    }

    public String getPurchaseAgent() {
        return purchaseAgent;
    }

    public void setPurchaseAgent(String purchaseAgent) {
        this.purchaseAgent = purchaseAgent == null ? null : purchaseAgent.trim();
    }

    public String getPurchaseContact() {
        return purchaseContact;
    }

    public void setPurchaseContact(String purchaseContact) {
        this.purchaseContact = purchaseContact == null ? null : purchaseContact.trim();
    }

    public String getPurchaseContactTelephone() {
        return purchaseContactTelephone;
    }

    public void setPurchaseContactTelephone(String purchaseContactTelephone) {
        this.purchaseContactTelephone = purchaseContactTelephone == null ? null : purchaseContactTelephone.trim();
    }

    public String getPurchaseContactAddress() {
        return purchaseContactAddress;
    }

    public void setPurchaseContactAddress(String purchaseContactAddress) {
        this.purchaseContactAddress = purchaseContactAddress == null ? null : purchaseContactAddress.trim();
    }

    public String getPurchaseUnitpostCode() {
        return purchaseUnitpostCode;
    }

    public void setPurchaseUnitpostCode(String purchaseUnitpostCode) {
        this.purchaseUnitpostCode = purchaseUnitpostCode == null ? null : purchaseUnitpostCode.trim();
    }

    public String getPurchasePayDep() {
        return purchasePayDep;
    }

    public void setPurchasePayDep(String purchasePayDep) {
        this.purchasePayDep = purchasePayDep == null ? null : purchasePayDep.trim();
    }

    public String getPurchaseBank() {
        return purchaseBank;
    }

    public void setPurchaseBank(String purchaseBank) {
        this.purchaseBank = purchaseBank == null ? null : purchaseBank.trim();
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
        this.supplierDepName = supplierDepName == null ? null : supplierDepName.trim();
    }

    public String getSupplierLegal() {
        return supplierLegal;
    }

    public void setSupplierLegal(String supplierLegal) {
        this.supplierLegal = supplierLegal == null ? null : supplierLegal.trim();
    }

    public String getSupplierAgent() {
        return supplierAgent;
    }

    public void setSupplierAgent(String supplierAgent) {
        this.supplierAgent = supplierAgent == null ? null : supplierAgent.trim();
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact == null ? null : supplierContact.trim();
    }

    public String getSuopplierContactTelephone() {
        return suopplierContactTelephone;
    }

    public void setSuopplierContactTelephone(String suopplierContactTelephone) {
        this.suopplierContactTelephone = suopplierContactTelephone == null ? null : suopplierContactTelephone.trim();
    }

    public String getSupplierContactAddress() {
        return supplierContactAddress;
    }

    public void setSupplierContactAddress(String supplierContactAddress) {
        this.supplierContactAddress = supplierContactAddress == null ? null : supplierContactAddress.trim();
    }

    public String getSupplierUnitpostCode() {
        return supplierUnitpostCode;
    }

    public void setSupplierUnitpostCode(String supplierUnitpostCode) {
        this.supplierUnitpostCode = supplierUnitpostCode == null ? null : supplierUnitpostCode.trim();
    }

    public String getSupplierBank() {
        return supplierBank;
    }

    public void setSupplierBank(String supplierBank) {
        this.supplierBank = supplierBank == null ? null : supplierBank.trim();
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
        this.supplierBankName = supplierBankName == null ? null : supplierBankName.trim();
    }
}