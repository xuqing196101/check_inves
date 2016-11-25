package ses.model.sms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商修改实体类
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public class SupplierEdit {
    /**
     * 主键
     */
    private String id;
    /**
     * 供应商id
     */
    private String recordId;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 企业网址
     */
    private String website;
    /**
     * 成立日期
     */
    private Date foundDate;
    /**
     * 营业类型
     */
    private String businessType;
    /**
     * 注册地址
     */
    private String address;
    /**
     * 开户行名称
     */
    private String bankName;
    /**
     * 开户行账号
     */
    private String bankAccount;
    /**
     * 邮编
     */
    private String postCode;
    /**
     * 附件
     */
    private String taxCert;
    /**
     * 附件
     */
    private String billCert;
    /**
     * 附件
     */
    private String securityCert;
    /**
     * 附件
     */
    private String breachCert;
    /**
     * 法人姓名
     */
    private String legalName;
    /**
     * 法人身份证号码
     */
    private String legalIdCard;
    /**
     * 法人电话
     */
    private String legalTelephone;
    /**
     * 法人手机
     */
    private String legalMobile;
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机
     */
    private String contactTelephone;
    /**
     * 传真
     */
    private String contactFax;
    /**
     * 联系人电话
     */
    private String contactMobile;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 联系人地址
     */
    private String contactAddress;
    /**
     * 统一社会唯一编码
     */
    private String creditCode;
    /**
     * 
     */
    private String registAuthority;
    /**
     * 注册资本
     */
    private BigDecimal registFund;
    /**
     * 营业开始日期
     */
    private Date businessStartDate;
    /**
     * 营业截止日期
     */
    private Date businessEndDate;
    /**
     * 营业范围
     */
    private String businessScope;
    /**
     * 营业地址
     */
    private String businessAddress;
    /**
     * 邮编
     */
    private Integer businessPostCode;
    /**
     * 是否为境外分支
     */
    private Short overseasBranch;
    /**
     * 国家
     */
    private String branchCountry;
    /**
     * 分支地址
     */
    private String branchAddress;
    /**
     * 分支名称
     */
    private String branchName;
    /**
     * 附件
     */
    private String businessCert;
    /**
     * 分值经营范围
     */
    private String branchBusinessScope;
    /**
     * 创建时间
     */
    private Timestamp createDate;
    /**
     *状态 
     */   
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