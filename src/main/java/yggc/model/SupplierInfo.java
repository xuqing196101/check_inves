package yggc.model;

import java.util.Date;

public class SupplierInfo {
    private Long id;

    private String loginName;

    private String mobile;

    private String password;

    private String supplierName;

    private String netUrl;

    private Date foundDate;

    private String businessType;

    private String address;

    private String bangkName;

    private String bankAccount;

    private String supplierZipCode;

    private String taxCert;

    private String billCert;

    private String securityCert;

    private String breachCert;

    private String legalName;

    private String legaIdcard;

    private Long legalMobile;

    private Long legalPhone;

    private String contactName;

    private String contactMobile;

    private String contactFax;

    private Long contactPhone;

    private String contactEmail;

    private String contactAddress;

    private String creditCode;

    private String registAuthority;

    private Long registFund;

    private Date businessStartDate;

    private Date businessEndDate;

    private String businessScope;

    private String businessAddress;

    private Integer businessZipCode;

    private Short overseasBranch;

    private String branchCountry;

    private String branchAddress;

    private String branchName;

    private String branchBusinessScope;

    private Long supplierTypeId;

    private Long status;

    private Long orgmanId;

    private String supplierLevel;

    private String supplierPledge;

    private String supplierRegList;

    private String supplierExtractsList;

    private String supplierInspectList;

    private String supplierReviewList;

    private String supplierChangeList;

    private String supplierExitList;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getNetUrl() {
        return netUrl;
    }

    public void setNetUrl(String netUrl) {
        this.netUrl = netUrl == null ? null : netUrl.trim();
    }

    public Date getFoundDate() {
        return foundDate;
    }

    public void setFoundDate(Date foundDate) {
        this.foundDate = foundDate;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getBangkName() {
        return bangkName;
    }

    public void setBangkName(String bangkName) {
        this.bangkName = bangkName == null ? null : bangkName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getSupplierZipCode() {
        return supplierZipCode;
    }

    public void setSupplierZipCode(String supplierZipCode) {
        this.supplierZipCode = supplierZipCode == null ? null : supplierZipCode.trim();
    }

    public String getTaxCert() {
        return taxCert;
    }

    public void setTaxCert(String taxCert) {
        this.taxCert = taxCert == null ? null : taxCert.trim();
    }

    public String getBillCert() {
        return billCert;
    }

    public void setBillCert(String billCert) {
        this.billCert = billCert == null ? null : billCert.trim();
    }

    public String getSecurityCert() {
        return securityCert;
    }

    public void setSecurityCert(String securityCert) {
        this.securityCert = securityCert == null ? null : securityCert.trim();
    }

    public String getBreachCert() {
        return breachCert;
    }

    public void setBreachCert(String breachCert) {
        this.breachCert = breachCert == null ? null : breachCert.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getLegaIdcard() {
        return legaIdcard;
    }

    public void setLegaIdcard(String legaIdcard) {
        this.legaIdcard = legaIdcard == null ? null : legaIdcard.trim();
    }

    public Long getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(Long legalMobile) {
        this.legalMobile = legalMobile;
    }

    public Long getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(Long legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax == null ? null : contactFax.trim();
    }

    public Long getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(Long contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail == null ? null : contactEmail.trim();
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress == null ? null : contactAddress.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getRegistAuthority() {
        return registAuthority;
    }

    public void setRegistAuthority(String registAuthority) {
        this.registAuthority = registAuthority == null ? null : registAuthority.trim();
    }

    public Long getRegistFund() {
        return registFund;
    }

    public void setRegistFund(Long registFund) {
        this.registFund = registFund;
    }

    public Date getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(Date businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public Date getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(Date businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope == null ? null : businessScope.trim();
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress == null ? null : businessAddress.trim();
    }

    public Integer getBusinessZipCode() {
        return businessZipCode;
    }

    public void setBusinessZipCode(Integer businessZipCode) {
        this.businessZipCode = businessZipCode;
    }

    public Short getOverseasBranch() {
        return overseasBranch;
    }

    public void setOverseasBranch(Short overseasBranch) {
        this.overseasBranch = overseasBranch;
    }

    public String getBranchCountry() {
        return branchCountry;
    }

    public void setBranchCountry(String branchCountry) {
        this.branchCountry = branchCountry == null ? null : branchCountry.trim();
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress == null ? null : branchAddress.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getBranchBusinessScope() {
        return branchBusinessScope;
    }

    public void setBranchBusinessScope(String branchBusinessScope) {
        this.branchBusinessScope = branchBusinessScope == null ? null : branchBusinessScope.trim();
    }

    public Long getSupplierTypeId() {
        return supplierTypeId;
    }

    public void setSupplierTypeId(Long supplierTypeId) {
        this.supplierTypeId = supplierTypeId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getOrgmanId() {
        return orgmanId;
    }

    public void setOrgmanId(Long orgmanId) {
        this.orgmanId = orgmanId;
    }

    public String getSupplierLevel() {
        return supplierLevel;
    }

    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel == null ? null : supplierLevel.trim();
    }

    public String getSupplierPledge() {
        return supplierPledge;
    }

    public void setSupplierPledge(String supplierPledge) {
        this.supplierPledge = supplierPledge == null ? null : supplierPledge.trim();
    }

    public String getSupplierRegList() {
        return supplierRegList;
    }

    public void setSupplierRegList(String supplierRegList) {
        this.supplierRegList = supplierRegList == null ? null : supplierRegList.trim();
    }

    public String getSupplierExtractsList() {
        return supplierExtractsList;
    }

    public void setSupplierExtractsList(String supplierExtractsList) {
        this.supplierExtractsList = supplierExtractsList == null ? null : supplierExtractsList.trim();
    }

    public String getSupplierInspectList() {
        return supplierInspectList;
    }

    public void setSupplierInspectList(String supplierInspectList) {
        this.supplierInspectList = supplierInspectList == null ? null : supplierInspectList.trim();
    }

    public String getSupplierReviewList() {
        return supplierReviewList;
    }

    public void setSupplierReviewList(String supplierReviewList) {
        this.supplierReviewList = supplierReviewList == null ? null : supplierReviewList.trim();
    }

    public String getSupplierChangeList() {
        return supplierChangeList;
    }

    public void setSupplierChangeList(String supplierChangeList) {
        this.supplierChangeList = supplierChangeList == null ? null : supplierChangeList.trim();
    }

    public String getSupplierExitList() {
        return supplierExitList;
    }

    public void setSupplierExitList(String supplierExitList) {
        this.supplierExitList = supplierExitList == null ? null : supplierExitList.trim();
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