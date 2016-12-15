package ses.model.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ses.model.bms.Area;
import ses.model.bms.CategoryParameter;
import ses.util.MyAnnotation;

/**
 * @Title: Supplier
 * @Description: 供应商实体类
 * @author: Wang Zhaohua
 * @date: 2016-9-13上午9:45:27
 */
public class Supplier implements Serializable {
	private static final long serialVersionUID = 8734428260706437179L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER.ID
	 * </pre>
	 */
	@MyAnnotation("")
	private String id;

	/**
	 * <pre>
	 * 登录名
	 * 表字段 : T_SES_SMS_SUPPLIER.LOGIN_NAME
	 * </pre>
	 */
	private String loginName;

	/**
	 * <pre>
	 * 手机号
	 * 表字段 : T_SES_SMS_SUPPLIER.MOBILE
	 * </pre>
	 */
	@MyAnnotation("联系人手机号")
	private String mobile;

	/**
	 * <pre>
	 * 密码
	 * 表字段 : T_SES_SMS_SUPPLIER.PASSWORD
	 * </pre>
	 */
	private String password;

	/**
	 * <pre>
	 * 供应商名称
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_NAME
	 * </pre>
	 */
	@MyAnnotation("供应商名称")
	private String supplierName;

	/**
	 * <pre>
	 * 网址
	 * 表字段 : T_SES_SMS_SUPPLIER.WEBSITE
	 * </pre>
	 */
	@MyAnnotation("网址")
	private String website;

	/**
	 * <pre>
	 * 成立日期
	 * 表字段 : T_SES_SMS_SUPPLIER.FOUND_DATE
	 * </pre>
	 */
	@MyAnnotation("成立日期")
	private Date foundDate;

	/**
	 * <pre>
	 * 营业执照类型
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_TYPE
	 * </pre>
	 */
	@MyAnnotation("营业执照类型")
	private String businessType;

	/**
	 * <pre>
	 * 营业执照类型
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_TYPE
	 * </pre>
	 */
	private String businessCert;

	/**
	 * <pre>
	 * 公司地址
	 * 表字段 : T_SES_SMS_SUPPLIER.ADDRESS
	 * </pre>
	 */
	@MyAnnotation("注册地址")
	private String address;

	/**
	 * <pre>
	 * 开户行名称
	 * 表字段 : T_SES_SMS_SUPPLIER.BANK_NAME
	 * </pre>
	 */
	@MyAnnotation("开户行名称")
	private String bankName;

	/**
	 * <pre>
	 * 开户行账号
	 * 表字段 : T_SES_SMS_SUPPLIER.BANK_ ACCOUNT
	 * </pre>
	 */
	@MyAnnotation("开户行账号")
	private String bankAccount;

	/**
	 * <pre>
	 * 邮编
	 * 表字段 : T_SES_SMS_SUPPLIER.POST_CODE
	 * </pre>
	 */
	@MyAnnotation("注册邮编")
	private String postCode;

	/**
	 * <pre>
	 * 税凭证
	 * 表字段 : T_SES_SMS_SUPPLIER.TAX_CERT
	 * </pre>
	 */
	private String taxCert;

	/**
	 * <pre>
	 * 年末对账单
	 * 表字段 : T_SES_SMS_SUPPLIER.BILL_CERT
	 * </pre>
	 */
	private String billCert;

	/**
	 * <pre>
	 * 保险金凭证
	 * 表字段 : T_SES_SMS_SUPPLIER.SECURITY_CERT
	 * </pre>
	 */
	private String securityCert;

	/**
	 * <pre>
	 * 违纪记录声明
	 * 表字段 : T_SES_SMS_SUPPLIER.BREACH_CERT
	 * </pre>
	 */
	private String breachCert;

	/**
	 * <pre>
	 * 法人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER.LEGAL_NAME
	 * </pre>
	 */
	@MyAnnotation("法人姓名")
	private String legalName;

	/**
	 * <pre>
	 * 法人身份证号
	 * 表字段 : T_SES_SMS_SUPPLIER.LEGA_ID_CARD
	 * </pre>
	 */
	@MyAnnotation("法人身份证号")
	private String legalIdCard;

	/**
	 * <pre>
	 * 法人固定电话
	 * 表字段 : T_SES_SMS_SUPPLIER.LEGAL_TELEPHONE
	 * </pre>
	 */
	@MyAnnotation("法人手机号")
	private String legalTelephone;

	/**
	 * <pre>
	 * 法人手机号
	 * 表字段 : T_SES_SMS_SUPPLIER.LEGAL_MOBILE
	 * </pre>
	 */
	@MyAnnotation("法人固定电话")
	private String legalMobile;

	/**
	 * <pre>
	 * 联系人姓名
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_NAME
	 * </pre>
	 */
	@MyAnnotation("注册联系人姓名")
	private String contactName;

	/**
	 * <pre>
	 * 联系电话
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_TELEPHONE
	 * </pre>
	 */
	@MyAnnotation("注册联系人手机号")
	private String contactTelephone;

	/**
	 * <pre>
	 * 联系人传真
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_FAX
	 * </pre>
	 */
	@MyAnnotation("注册联系人传真")
	private String contactFax;

	/**
	 * <pre>
	 * 联系人手机
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_MOBILE
	 * </pre>
	 */
	@MyAnnotation("注册联系人固定电话")
	private String contactMobile;

	/**
	 * <pre>
	 * 联系人邮箱
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_EMAIL
	 * </pre>
	 */
	@MyAnnotation("注册联系人邮箱")
	private String contactEmail;

	/**
	 * <pre>
	 * 联系人地址
	 * 表字段 : T_SES_SMS_SUPPLIER.CONTACT_ADDRESS
	 * </pre>
	 */
	@MyAnnotation("注册联系人邮箱")
	private String contactAddress;

	/**
	 * <pre>
	 * 统一社会信用代码
	 * 表字段 : T_SES_SMS_SUPPLIER.CREDIT_CODE
	 * </pre>
	 */
	private String creditCode;

	/**
	 * <pre>
	 * 登记机关
	 * 表字段 : T_SES_SMS_SUPPLIER.REGIST_AUTHORITY
	 * </pre>
	 */
	private String registAuthority;

	/**
	 * <pre>
	 * 注册资金
	 * 表字段 : T_SES_SMS_SUPPLIER.REGIST_FUND
	 * </pre>
	 */
	private BigDecimal registFund;

	/**
	 * <pre>
	 * 营业起始日期
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_START_DATE
	 * </pre>
	 */
	private Date businessStartDate;

	/**
	 * <pre>
	 * 营业结束日期
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_END_DATE
	 * </pre>
	 */
	private Date businessEndDate;

	/**
	 * <pre>
	 * 营业范围
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_SCOPE
	 * </pre>
	 */
	private String businessScope;

	/**
	 * <pre>
	 * 经营地址
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_ADDRESS
	 * </pre>
	 */
	private String businessAddress;

	/**
	 * <pre>
	 * 经营地址邮编
	 * 表字段 : T_SES_SMS_SUPPLIER.BUSINESS_POST_CODE
	 * </pre>
	 */
	private Integer businessPostCode;

	/**
	 * <pre>
	 * 境外分支机构 0代表无  1代表有
	 * 表字段 : T_SES_SMS_SUPPLIER.OVERSEAS_BRANCH
	 * </pre>
	 */
	private Integer overseasBranch;

	/**
	 * <pre>
	 * 分支所在国家
	 * 表字段 : T_SES_SMS_SUPPLIER.BRANCH_COUNTRY
	 * </pre>
	 */
	private String branchCountry;

	/**
	 * <pre>
	 * 分支地址
	 * 表字段 : T_SES_SMS_SUPPLIER.BRANCH_ADDRESS
	 * </pre>
	 */
	private String branchAddress;

	/**
	 * <pre>
	 * 分支名称
	 * 表字段 : T_SES_SMS_SUPPLIER.BRANCH_NAME
	 * </pre>
	 */
	private String branchName;

	/**
	 * <pre>
	 * 分支经营范围
	 * 表字段 : T_SES_SMS_SUPPLIER.BRANCH_BUSINESS_SCOPE
	 * </pre>
	 */
	private String branchBusinessScope;

	/**
	 * <pre>
	 * 供应商类别ID   T_SES_SMS_SUPPLIER_TYPES
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_TYPE_ID
	 * </pre>
	 */
	private String supplierTypeId;

	/**
	 * <pre>
	 * 供应商状态 0代表待审核 1表审核通过 2代表未通过 3代表变更
	 * 表字段 : T_SES_SMS_SUPPLIER.STATUS
	 * </pre>
	 */
	private Integer status;

	/**
	 * <pre>
	 * 机构ID  T_SES_OMS_PROCUREMENT_DEP
	 * 表字段 : T_SES_SMS_SUPPLIER.PROCUREMENT_ID
	 * </pre>
	 */
	private String procurementDepId;

	/**
	 * <pre>
	 * 供应商分级办法 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_LEVEL
	 * </pre>
	 */
	private String supplierLevel;

	/**
	 * <pre>
	 * 供应商承诺书 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_PLEDGE
	 * </pre>
	 */
	private String supplierPledge;

	/**
	 * <pre>
	 * 供应商入库申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_REG_LIST
	 * </pre>
	 */
	private String supplierRegList;

	/**
	 * <pre>
	 * 供应商抽取记录表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_EXTRACTS_LIST
	 * </pre>
	 */
	private String supplierExtractsList;

	/**
	 * <pre>
	 * 供应商考察记录表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_INSPECT_LIST
	 * </pre>
	 */
	private String supplierInspectList;

	/**
	 * <pre>
	 * 供应商考察廉政意见函 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_REVIEW_LIST
	 * </pre>
	 */
	private String supplierReviewList;

	/**
	 * <pre>
	 * 供应商变更申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_CHANGE_LIST
	 * </pre>
	 */
	private String supplierChangeList;

	/**
	 * <pre>
	 * 供应商退库申请表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER.SUPPLIER_EXIT_LIST
	 * </pre>
	 */
	private String supplierExitList;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private List<SupplierFinance> listSupplierFinances = new ArrayList<SupplierFinance>();

	private List<SupplierStockholder> listSupplierStockholders = new ArrayList<SupplierStockholder>();

	private SupplierMatPro supplierMatPro;

	private SupplierMatSell supplierMatSell;

	private SupplierMatServe supplierMatSe;

	private SupplierMatEng supplierMatEng;

	private String supplierTypeIds;

	private String supplierItemIds;

	private String supplierTypeNames;

	private List<SupplierTypeRelate> listSupplierTypeRelates = new ArrayList<SupplierTypeRelate>();

	private Date passDate;

	private Date startDate;

	private Date endDate;

	private String supplierType;

	private List<String> item;

	private Integer count;

	private Integer score;

	private Integer startScore;

	private Integer endScore;

	private List<SupplierItem> listSupplierItems = new ArrayList<SupplierItem>();

	private String level;

	private List<SupplierProducts> listSupplierProducts = new ArrayList<SupplierProducts>();

	private String confirmPassword;

	private String mobileCode;

	private String identifyCode;
	
	private List<String> itemType;
	
	private Integer sign;  //审核标志
	
	private Integer page;
	
	private Integer rows;
	
	private String businessTypeName; //营业执照类型名字
	
	private String isExtract; //是否抽取
	
	/**获取所有数据库address的省级单位名称*/
	private String name;
	
	/**地址全名*/
	private String addressName;

	/**发售表书里面已经存在供应商id*/
	private List<String> stsupplierIds;
	
	/**投标文件状态*/
	private String bidFinish;
	//品目参数
	private List<CategoryParameter>  categoryParam= new ArrayList<CategoryParameter>();
	
	
	private List<ProductParam> paramVleu=new   ArrayList<ProductParam>();
	/**
	 * 公司详细地址
	 */
	@MyAnnotation("注册详细地址")
	private String detailAddress;
	/**
	 * 条件查询供应商等级
	 */
	private String scoreStart;
	/**
	 * 条件查询供应商等级
	 */
	private String scoreEnd;
	
	private String armyBusinessName;
	
	private String armyBusinessFax;
	
	private String armyBuinessMobile;
	
	private String armyBuinessTelephone;
	
	private String armyBuinessEmail;
	
	private String armyBuinessAddress;
	
	private Date auditDate;
	
	private List<SupplierAddress> addressList=new ArrayList<SupplierAddress>();
	
	private List<SupplierBranch> branchList=new ArrayList<SupplierBranch>();
	
	private String concatProvince;
	
	
	private String concatCity;
	
	private String armyBuinessProvince;
	
	private String armyBuinessCity;
	
	private List<Area> concatCityList=new ArrayList<Area>();
	
	private List<Area> armyCity=new ArrayList<Area>();
	
	public String getScoreStart() {
        return scoreStart;
    }

    public void setScoreStart(String scoreStart) {
        this.scoreStart = scoreStart;
    }

    public String getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(String scoreEnd) {
        this.scoreEnd = scoreEnd;
    }

    public List<CategoryParameter> getCategoryParam() {
		return categoryParam;
	}

	public void setCategoryParam(List<CategoryParameter> categoryParam) {
		this.categoryParam = categoryParam;
	}

	public String getBidFinish() {
		return bidFinish;
	}

	public void setBidFinish(String bidFinish) {
		this.bidFinish = bidFinish;
	}

	public List<String> getStsupplierIds() {
		return stsupplierIds;
	}

	public void setStsupplierIds(List<String> stsupplierIds) {
		this.stsupplierIds = stsupplierIds;
	}
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getItemType() {
		return itemType;
	}

	public void setItemType(List<String> itemType) {
		this.itemType = itemType;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<String> getItem() {
		return item;
	}

	public void setItem(List<String> item) {
		this.item = item;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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

	public String getLegalIdCard() {
		return legalIdCard;
	}

	public void setLegalIdCard(String legalIdCard) {
		this.legalIdCard = legalIdCard;
	}

	public String getLegalTelephone() {
		return legalTelephone;
	}

	public void setLegalTelephone(String legalTelephone) {
		this.legalTelephone = legalTelephone;
	}

	public String getLegalMobile() {
		return legalMobile;
	}

	public void setLegalMobile(String legalMobile) {
		this.legalMobile = legalMobile;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactTelephone() {
		return contactTelephone;
	}

	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}

	public String getContactFax() {
		return contactFax;
	}

	public void setContactFax(String contactFax) {
		this.contactFax = contactFax;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
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

	public Integer getBusinessPostCode() {
		return businessPostCode;
	}

	public void setBusinessPostCode(Integer businessPostCode) {
		this.businessPostCode = businessPostCode;
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

	public String getSupplierTypeId() {
		return supplierTypeId;
	}

	public void setSupplierTypeId(String supplierTypeId) {
		this.supplierTypeId = supplierTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProcurementDepId() {
		return procurementDepId;
	}

	public void setProcurementDepId(String procurementDepId) {
		this.procurementDepId = procurementDepId;
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

	public List<SupplierFinance> getListSupplierFinances() {
		return listSupplierFinances;
	}

	public void setListSupplierFinances(List<SupplierFinance> listSupplierFinances) {
		this.listSupplierFinances = listSupplierFinances;
	}

	public List<SupplierStockholder> getListSupplierStockholders() {
		return listSupplierStockholders;
	}

	public void setListSupplierStockholders(List<SupplierStockholder> listSupplierStockholders) {
		this.listSupplierStockholders = listSupplierStockholders;
	}

	public SupplierMatPro getSupplierMatPro() {
		return supplierMatPro;
	}

	public void setSupplierMatPro(SupplierMatPro supplierMatPro) {
		this.supplierMatPro = supplierMatPro;
	}

	public SupplierMatSell getSupplierMatSell() {
		return supplierMatSell;
	}

	public void setSupplierMatSell(SupplierMatSell supplierMatSell) {
		this.supplierMatSell = supplierMatSell;
	}

	public SupplierMatServe getSupplierMatSe() {
		return supplierMatSe;
	}

	public void setSupplierMatSe(SupplierMatServe supplierMatSe) {
		this.supplierMatSe = supplierMatSe;
	}

	public SupplierMatEng getSupplierMatEng() {
		return supplierMatEng;
	}

	public void setSupplierMatEng(SupplierMatEng supplierMatEng) {
		this.supplierMatEng = supplierMatEng;
	}

	public String getSupplierTypeNames() {
		return supplierTypeNames;
	}

	public void setSupplierTypeNames(String supplierTypeNames) {
		this.supplierTypeNames = supplierTypeNames;
	}

	public String getSupplierTypeIds() {
		return supplierTypeIds;
	}

	public void setSupplierTypeIds(String supplierTypeIds) {
		this.supplierTypeIds = supplierTypeIds;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}

	public Date getPassDate() {
		return passDate;
	}

	public void setPassDate(Date passDate) {
		this.passDate = passDate;
	}

	public List<SupplierTypeRelate> getListSupplierTypeRelates() {
		return listSupplierTypeRelates;
	}

	public void setListSupplierTypeRelates(List<SupplierTypeRelate> listSupplierTypeRelates) {
		this.listSupplierTypeRelates = listSupplierTypeRelates;
	}

	public String getSupplierItemIds() {
		return supplierItemIds;
	}

	public void setSupplierItemIds(String supplierItemIds) {
		this.supplierItemIds = supplierItemIds;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<SupplierItem> getListSupplierItems() {
		return listSupplierItems;
	}

	public void setListSupplierItems(List<SupplierItem> listSupplierItems) {
		this.listSupplierItems = listSupplierItems;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getStartScore() {
		return startScore;
	}

	public void setStartScore(Integer startScore) {
		this.startScore = startScore;
	}

	public Integer getEndScore() {
		return endScore;
	}

	public void setEndScore(Integer endScore) {
		this.endScore = endScore;
	}

	public List<SupplierProducts> getListSupplierProducts() {
		return listSupplierProducts;
	}

	public void setListSupplierProducts(List<SupplierProducts> listSupplierProducts) {
		this.listSupplierProducts = listSupplierProducts;
	}

	public String getBusinessCert() {
		return businessCert;
	}

	public void setBusinessCert(String businessCert) {
		this.businessCert = businessCert;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public List<ProductParam> getParamVleu() {
		return paramVleu;
	}

	public void setParamVleu(List<ProductParam> paramVleu) {
		this.paramVleu = paramVleu;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getArmyBusinessName() {
		return armyBusinessName;
	}

	public void setArmyBusinessName(String armyBusinessName) {
		this.armyBusinessName = armyBusinessName;
	}

	public String getArmyBusinessFax() {
		return armyBusinessFax;
	}

	public void setArmyBusinessFax(String armyBusinessFax) {
		this.armyBusinessFax = armyBusinessFax;
	}

	public String getArmyBuinessMobile() {
		return armyBuinessMobile;
	}

	public void setArmyBuinessMobile(String armyBuinessMobile) {
		this.armyBuinessMobile = armyBuinessMobile;
	}

	public String getArmyBuinessTelephone() {
		return armyBuinessTelephone;
	}

	public void setArmyBuinessTelephone(String armyBuinessTelephone) {
		this.armyBuinessTelephone = armyBuinessTelephone;
	}

	public String getArmyBuinessEmail() {
		return armyBuinessEmail;
	}

	public void setArmyBuinessEmail(String armyBuinessEmail) {
		this.armyBuinessEmail = armyBuinessEmail;
	}

	public String getArmyBuinessAddress() {
		return armyBuinessAddress;
	}

	public void setArmyBuinessAddress(String armyBuinessAddress) {
		this.armyBuinessAddress = armyBuinessAddress;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public List<SupplierAddress> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<SupplierAddress> addressList) {
		this.addressList = addressList;
	}
	public String getBusinessTypeName() {
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName) {
		this.businessTypeName = businessTypeName;
	}

	public String getIsExtract() {
		return isExtract;
	}

	public void setIsExtract(String isExtract) {
		this.isExtract = isExtract;
	}

	public List<SupplierBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<SupplierBranch> branchList) {
		this.branchList = branchList;
	}

	public String getConcatProvince() {
		return concatProvince;
	}

	public void setConcatProvince(String concatProvince) {
		this.concatProvince = concatProvince;
	}

	public String getConcatCity() {
		return concatCity;
	}

	public void setConcatCity(String concateCity) {
		this.concatCity = concateCity;
	}

	public String getArmyBuinessProvince() {
		return armyBuinessProvince;
	}

	public void setArmyBuinessProvince(String armyBuinessProvince) {
		this.armyBuinessProvince = armyBuinessProvince;
	}

	public String getArmyBuinessCity() {
		return armyBuinessCity;
	}

	public void setArmyBuinessCity(String armyBuinessCity) {
		this.armyBuinessCity = armyBuinessCity;
	}

	public List<Area> getConcatCityList() {
		return concatCityList;
	}

	public void setConcatCityList(List<Area> concatCityList) {
		this.concatCityList = concatCityList;
	}

	public List<Area> getArmyCity() {
		return armyCity;
	}

	public void setArmyCity(List<Area> armyCity) {
		this.armyCity = armyCity;
	}
	
	
	
	
}