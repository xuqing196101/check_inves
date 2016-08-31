package yggc.model;

import java.math.BigDecimal;
import java.util.Date;

public class SupplierChange {
    private Long id;

    private String loginName;

    private Long mobile;

    private String supplierName;

    private String netUrl;

    private Date foundDate;

    private String businessType;

    private String address;

    private String bangkName;

    private String bankAccount;

    private Integer supplierZipCode;

    private String taxCert;

    private String billCert;

    private String securityCert;

    private String breachCert;

    private String legalName;

    private String legaIdcard;

    private Long legalMobile;

    private BigDecimal legalPhone;

    private String contactName;

    private String contactMobile;

    private Long contactFax;

    private Long contactPhone;

    private String contactEmail;

    private String contactAddress;

    private String creditCode;

    private String registAuthority;

    private BigDecimal registFund;

    private Date businessStartDate;

    private Date businessEndDate;

    private String businessScope;

    private String businessAddress;

    private String businessZipCode;

    private Long overseasBranch;

    private String branchCountry;

    private String branchAddress;

    private String branchName;

    private String branchBusinessScope;

    private Long supplierTypeId;

    private Short status;

    private Long supplierItemsId;

    private Date createdAt;

    private Date updatedAt;

    private String password;

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

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
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

    public Integer getSupplierZipCode() {
        return supplierZipCode;
    }

    public void setSupplierZipCode(Integer supplierZipCode) {
        this.supplierZipCode = supplierZipCode;
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

    public BigDecimal getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(BigDecimal legalPhone) {
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

    public Long getContactFax() {
        return contactFax;
    }

    public void setContactFax(Long contactFax) {
        this.contactFax = contactFax;
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

    public BigDecimal getRegistFund() {
        return registFund;
    }

    public void setRegistFund(BigDecimal registFund) {
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

    public String getBusinessZipCode() {
        return businessZipCode;
    }

    public void setBusinessZipCode(String businessZipCode) {
        this.businessZipCode = businessZipCode == null ? null : businessZipCode.trim();
    }

    public Long getOverseasBranch() {
        return overseasBranch;
    }

    public void setOverseasBranch(Long overseasBranch) {
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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getSupplierItemsId() {
        return supplierItemsId;
    }

    public void setSupplierItemsId(Long supplierItemsId) {
        this.supplierItemsId = supplierItemsId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}