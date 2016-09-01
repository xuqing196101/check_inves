package yggc.model.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierInfo implements Serializable {
	private static final long serialVersionUID = 1198428984275872724L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.ID
	 * </pre>
	 */
	private Integer id;

	/**
	 * <pre>
	 * 登录名
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.LOGIN_NAME
	 * </pre>
	 */
	private String loginName;

	/**
	 * <pre>
	 * 手机号
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.MOBILE
	 * </pre>
	 */
	private String mobile;

	/**
	 * <pre>
	 * 密码
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.PASSWORD
	 * </pre>
	 */
	private String password;

	/**
	 * <pre>
	 * 供应商名称
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_NAME
	 * </pre>
	 */
	private String supplierName;

	/**
	 * <pre>
	 * 网址
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.NET_URL
	 * </pre>
	 */
	private String netUrl;

	/**
	 * <pre>
	 * 成立日期
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.FOUND_DATE
	 * </pre>
	 */
	private Date foundDate;

	/**
	 * <pre>
	 * 营业执照类型
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_TYPE
	 * </pre>
	 */
	private String businessType;

	/**
	 * <pre>
	 * 公司地址
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.ADDRESS
	 * </pre>
	 */
	private String address;

	/**
	 * <pre>
	 * 开户行名称
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BANGK_NAME
	 * </pre>
	 */
	private String bangkName;

	/**
	 * <pre>
	 * 开户行账号
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BANK_ ACCOUNT
	 * </pre>
	 */
	private String bankAccount;

	/**
	 * <pre>
	 * 邮编
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_ZIP_CODE
	 * </pre>
	 */
	private String supplierZipCode;

	/**
	 * <pre>
	 * 税凭证
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.TAX_CERT
	 * </pre>
	 */
	private String taxCert;

	/**
	 * <pre>
	 * 年末对账单
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BILL_CERT
	 * </pre>
	 */
	private String billCert;

	/**
	 * <pre>
	 * 保险金凭证
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SECURITY_CERT
	 * </pre>
	 */
	private String securityCert;

	/**
	 * <pre>
	 * 违纪记录声明
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BREACH_CERT
	 * </pre>
	 */
	private String breachCert;

	/**
	 * <pre>
	 * 法人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.LEGAL_NAME
	 * </pre>
	 */
	private String legalName;

	/**
	 * <pre>
	 * 法人身份证号
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.LEGA_IDCARD
	 * </pre>
	 */
	private String legaIdcard;

	/**
	 * <pre>
	 * 法人固定电话
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.LEGAL_MOBILE
	 * </pre>
	 */
	private Long legalMobile;

	/**
	 * <pre>
	 * 法人手机号
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.LEGAL_PHONE
	 * </pre>
	 */
	private String legalPhone;

	/**
	 * <pre>
	 * 联系人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_NAME
	 * </pre>
	 */
	private String contactName;

	/**
	 * <pre>
	 * 联系电话
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_MOBILE
	 * </pre>
	 */
	private String contactMobile;

	/**
	 * <pre>
	 * 联系人传真
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_FAX
	 * </pre>
	 */
	private String contactFax;

	/**
	 * <pre>
	 * 联系人手机
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_PHONE
	 * </pre>
	 */
	private Long contactPhone;

	/**
	 * <pre>
	 * 联系人邮箱
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_EMAIL
	 * </pre>
	 */
	private String contactEmail;

	/**
	 * <pre>
	 * 联系人地址
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CONTACT_ADDRESS
	 * </pre>
	 */
	private String contactAddress;

	/**
	 * <pre>
	 * 统一社会信用代码
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CREDIT_CODE
	 * </pre>
	 */
	private String creditCode;

	/**
	 * <pre>
	 * 登记机关
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.REGIST_AUTHORITY
	 * </pre>
	 */
	private String registAuthority;

	/**
	 * <pre>
	 * 注册资金
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.REGIST_FUND
	 * </pre>
	 */
	private BigDecimal registFund;

	/**
	 * <pre>
	 * 营业起始日期
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_START_DATE
	 * </pre>
	 */
	private Date businessStartDate;

	/**
	 * <pre>
	 * 营业结束日期
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_END_DATE
	 * </pre>
	 */
	private Date businessEndDate;

	/**
	 * <pre>
	 * 营业范围
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_SCOPE
	 * </pre>
	 */
	private String businessScope;

	/**
	 * <pre>
	 * 经营地址
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_ADDRESS
	 * </pre>
	 */
	private String businessAddress;

	/**
	 * <pre>
	 * 经营地址邮编
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BUSINESS_ZIP_CODE
	 * </pre>
	 */
	private String businessZipCode;

	/**
	 * <pre>
	 * 境外分支机构 0代表无  1代表有
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.OVERSEAS_BRANCH
	 * </pre>
	 */
	private Integer overseasBranch;

	/**
	 * <pre>
	 * 分支所在国家
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BRANCH_COUNTRY
	 * </pre>
	 */
	private String branchCountry;

	/**
	 * <pre>
	 * 分支地址
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BRANCH_ADDRESS
	 * </pre>
	 */
	private String branchAddress;

	/**
	 * <pre>
	 * 分支名称
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BRANCH_NAME
	 * </pre>
	 */
	private String branchName;

	/**
	 * <pre>
	 * 分支经营范围
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.BRANCH_BUSINESS_SCOPE
	 * </pre>
	 */
	private String branchBusinessScope;

	/**
	 * <pre>
	 * 供应商类别ID   T_SES_SMS_SUPPLIER_TYPES
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_TYPE_ID
	 * </pre>
	 */
	private Integer supplierTypeId;

	/**
	 * <pre>
	 * 供应商状态 0代表待审核 1表审核通过 2代表未通过 3代表变更
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.STATUS
	 * </pre>
	 */
	private Integer status;

	/**
	 * <pre>
	 * 机构ID  T_SES_OMS_PROCUREMENT_DEP
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.ORGMAN_ID
	 * </pre>
	 */
	private Integer orgmanId;

	/**
	 * <pre>
	 * 供应商分级办法 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_LEVEL
	 * </pre>
	 */
	private String supplierLevel;

	/**
	 * <pre>
	 * 供应商承诺书 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_PLEDGE
	 * </pre>
	 */
	private String supplierPledge;

	/**
	 * <pre>
	 * 供应商入库申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_REG_LIST
	 * </pre>
	 */
	private String supplierRegList;

	/**
	 * <pre>
	 * 供应商抽取记录表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_EXTRACTS_LIST
	 * </pre>
	 */
	private String supplierExtractsList;

	/**
	 * <pre>
	 * 供应商考察记录表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_INSPECT_LIST
	 * </pre>
	 */
	private String supplierInspectList;

	/**
	 * <pre>
	 * 供应商考察廉政意见函 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_REVIEW_LIST
	 * </pre>
	 */
	private String supplierReviewList;

	/**
	 * <pre>
	 * 供应商变更申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_CHANGE_LIST
	 * </pre>
	 */
	private String supplierChangeList;

	/**
	 * <pre>
	 * 供应商退库申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.SUPPLIER_EXIT_LIST
	 * </pre>
	 */
	private String supplierExitList;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_INFO.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getNetUrl() {
		return netUrl;
	}

	public void setNetUrl(String netUrl) {
		this.netUrl = netUrl;
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
		this.businessType = businessType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBangkName() {
		return bangkName;
	}

	public void setBangkName(String bangkName) {
		this.bangkName = bangkName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getSupplierZipCode() {
		return supplierZipCode;
	}

	public void setSupplierZipCode(String supplierZipCode) {
		this.supplierZipCode = supplierZipCode;
	}

	public String getTaxCert() {
		return taxCert;
	}

	public void setTaxCert(String taxCert) {
		this.taxCert = taxCert;
	}

	public String getBillCert() {
		return billCert;
	}

	public void setBillCert(String billCert) {
		this.billCert = billCert;
	}

	public String getSecurityCert() {
		return securityCert;
	}

	public void setSecurityCert(String securityCert) {
		this.securityCert = securityCert;
	}

	public String getBreachCert() {
		return breachCert;
	}

	public void setBreachCert(String breachCert) {
		this.breachCert = breachCert;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getLegaIdcard() {
		return legaIdcard;
	}

	public void setLegaIdcard(String legaIdcard) {
		this.legaIdcard = legaIdcard;
	}

	public Long getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(Long legalMobile) {
		this.legalMobile = legalMobile;
	}

	public String getLegalPhone() {
		return legalPhone;
	}

	public void setLegalPhone(String legalPhone) {
		this.legalPhone = legalPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
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
		this.contactEmail = contactEmail;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getRegistAuthority() {
		return registAuthority;
	}

	public void setRegistAuthority(String registAuthority) {
		this.registAuthority = registAuthority;
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
		this.businessScope = businessScope;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessZipCode() {
		return businessZipCode;
	}

	public void setBusinessZipCode(String businessZipCode) {
		this.businessZipCode = businessZipCode;
	}

	public Integer getOverseasBranch() {
		return overseasBranch;
	}

	public void setOverseasBranch(Integer overseasBranch) {
		this.overseasBranch = overseasBranch;
	}

	public String getBranchCountry() {
		return branchCountry;
	}

	public void setBranchCountry(String branchCountry) {
		this.branchCountry = branchCountry;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchBusinessScope() {
		return branchBusinessScope;
	}

	public void setBranchBusinessScope(String branchBusinessScope) {
		this.branchBusinessScope = branchBusinessScope;
	}

	public Integer getSupplierTypeId() {
		return supplierTypeId;
	}

	public void setSupplierTypeId(Integer supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrgmanId() {
		return orgmanId;
	}

	public void setOrgmanId(Integer orgmanId) {
		this.orgmanId = orgmanId;
	}

	public String getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(String supplierLevel) {
		this.supplierLevel = supplierLevel;
	}

	public String getSupplierPledge() {
		return supplierPledge;
	}

	public void setSupplierPledge(String supplierPledge) {
		this.supplierPledge = supplierPledge;
	}

	public String getSupplierRegList() {
		return supplierRegList;
	}

	public void setSupplierRegList(String supplierRegList) {
		this.supplierRegList = supplierRegList;
	}

	public String getSupplierExtractsList() {
		return supplierExtractsList;
	}

	public void setSupplierExtractsList(String supplierExtractsList) {
		this.supplierExtractsList = supplierExtractsList;
	}

	public String getSupplierInspectList() {
		return supplierInspectList;
	}

	public void setSupplierInspectList(String supplierInspectList) {
		this.supplierInspectList = supplierInspectList;
	}

	public String getSupplierReviewList() {
		return supplierReviewList;
	}

	public void setSupplierReviewList(String supplierReviewList) {
		this.supplierReviewList = supplierReviewList;
	}

	public String getSupplierChangeList() {
		return supplierChangeList;
	}

	public void setSupplierChangeList(String supplierChangeList) {
		this.supplierChangeList = supplierChangeList;
	}

	public String getSupplierExitList() {
		return supplierExitList;
	}

	public void setSupplierExitList(String supplierExitList) {
		this.supplierExitList = supplierExitList;
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