package ses.model.sms;

import java.sql.Timestamp;
import java.util.Date;

public class SupplierEdit {
    private String id;

    private String recordId;

    private String supplierName;

    private String website;

    private Date foundDate;

    private String businessType;

    private String address;

    private String bankName;

    private String bankAccount;

    private String postCode;

    private String taxCert;

    private String billCert;

    private String securityCert;

    private String breachCert;

    private String legalName;

    private String legalIdCard;

    private String legalTelephone;

    private String legalMobile;

    private String contactName;

    private String contactTelephone;

    private String contactFax;

    private String contactMobile;

    private String contactEmail;

    private String contactAddress;

    private String creditCode;

    private String registAuthority;

    private Long registFund;

    private Date businessStartDate;

    private Date businessEndDate;

    private String businessScope;

    private String businessAddress;

    private Integer businessPostCode;

    private Short overseasBranch;

    private String branchCountry;

    private String branchAddress;

    private String branchName;

    private String businessCert;

    private String branchBusinessScope;

    private Timestamp createDate;
    
    private short status;
    

    public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website == null ? null : website.trim();
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
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

    public String getLegalIdCard() {
        return legalIdCard;
    }

    public void setLegalIdCard(String legalIdCard) {
        this.legalIdCard = legalIdCard == null ? null : legalIdCard.trim();
    }

    public String getLegalTelephone() {
        return legalTelephone;
    }

    public void setLegalTelephone(String legalTelephone) {
        this.legalTelephone = legalTelephone == null ? null : legalTelephone.trim();
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile == null ? null : legalMobile.trim();
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactTelephone() {
        return contactTelephone;
    }

    public void setContactTelephone(String contactTelephone) {
        this.contactTelephone = contactTelephone == null ? null : contactTelephone.trim();
    }

    public String getContactFax() {
        return contactFax;
    }

    public void setContactFax(String contactFax) {
        this.contactFax = contactFax == null ? null : contactFax.trim();
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile == null ? null : contactMobile.trim();
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

    public Integer getBusinessPostCode() {
        return businessPostCode;
    }

    public void setBusinessPostCode(Integer businessPostCode) {
        this.businessPostCode = businessPostCode;
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

    public String getBusinessCert() {
        return businessCert;
    }

    public void setBusinessCert(String businessCert) {
        this.businessCert = businessCert == null ? null : businessCert.trim();
    }

    public String getBranchBusinessScope() {
        return branchBusinessScope;
    }

    public void setBranchBusinessScope(String branchBusinessScope) {
        this.branchBusinessScope = branchBusinessScope == null ? null : branchBusinessScope.trim();
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }
}