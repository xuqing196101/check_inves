package extract.model.supplier;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;



public class SupplierExtractProjectInfo {
	
	
	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.ID
	 * </pre>
	 */
	private String id;
	
	private String packageId;
	
	@Length(max=50)
	private String packageName;
	private String packageNumber;
	
	 /**
     * <pre>
     * 项目代码
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE
     * </pre>
     */
	@Length(min=1,max=50)
	@NotEmpty
    private String projectCode;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID
     * </pre>
     */
    private String projectId;
	
    @NotEmpty
    @Length(min=1,max=50)
	private String projectNumber;
    
    /**
     * 组织联系人
     */
    @NotEmpty
    @Length(min=1,max=50)
    private String contactPerson;

    @Pattern(regexp="^1[34578]\\d{9}$",message="格式不正确")
    @Length(min=11,max=11,message="格式不正确")
    private String contactNum;
    
    @Length(max=50)
    private String remark ;
    
    /**
     * <pre>
     * 项目名称
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME
     * </pre>
     */
    @NotEmpty(message="不能为空")
    @Length(min=1,max=50)
    private String projectName;

    /**
     * <pre>
     * 采购机构T_SES_OMS_PROCUREMENT_DEP
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROCUREMENT_DEP_ID
     * </pre>
     */
    private String procurementDepId;
    
    /**
     * 条件id  若是抽取中或者暂存 状态，会获取到上一次的条件信息
     */
    private String conditionId;

    /**
     * <pre>
     * 抽取时间
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_TIME
     * </pre>
     */
    private Date extractionTime;

    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_SITES
     * </pre>
     */
    private String extractionSites;

    /**
     * <pre>
     * 抽取方式 0语音抽取 1人工抽取
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACT_THE_WAY
     * </pre>
     */
    private Short extractTheWay;

    private Short status;
    
    

    /**
     * <pre>
     * 创建时间格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;
    
    private String extractUser;
    
    /**
     * <pre>
     * 开标日期格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT
     * </pre>
     */
    private Date tenderAt;

    /**
     * 采购方式  id
     */
    @NotEmpty
    private String purchaseType ;
    
    /**
     * 地区信息
     */
    private String areaName;
    
    /**
     * 项目类型
     */
    @NotEmpty
    private String projectType;
    
    /**
     * 项目施工地址省id
     */
    private String constructionPro;
    
    /**
     * 项目施工地址市
     */
    private String constructionAddr;
    
    /**
     * 售领起始时间
     */
    @NotNull(message="不能为空")
    private Date sellBegin;
    /**
     * 售领结束时间
     */
    @Future(message="日期不能小于当前日期")
    @NotNull(message="不能为空")
    private Date sellEnd;
    /**
     * 售领省
     */
    @NotEmpty
    private String  sellProvince;
    /**
     * 售领地址
     */
    @NotEmpty
    private String  sellAddress;
    /**
     * 售领详细地址
     */
    @NotEmpty
    @Length(min=1,max=50)
    private String sellSite;
    
    /**
     * 其他要求
     */
    private String elseInfo;
    
    /**
     * 是否删除
     */
    private Short isDelete;

    
    private String purchaseTypeName;

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getPackageId() {
		return packageId;
	}


	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String getPackageNumber() {
		return packageNumber;
	}


	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}


	public String getProjectCode() {
		return projectCode;
	}


	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}


	public String getProjectId() {
		return projectId;
	}


	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}


	public String getProjectNumber() {
		return projectNumber;
	}


	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProcurementDepId() {
		return procurementDepId;
	}


	public void setProcurementDepId(String procurementDepId) {
		this.procurementDepId = procurementDepId;
	}


	public String getConditionId() {
		return conditionId;
	}


	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}


	public Date getExtractionTime() {
		return extractionTime;
	}


	public void setExtractionTime(Date extractionTime) {
		this.extractionTime = extractionTime;
	}


	public String getExtractionSites() {
		return extractionSites;
	}


	public void setExtractionSites(String extractionSites) {
		this.extractionSites = extractionSites;
	}


	public Short getExtractTheWay() {
		return extractTheWay;
	}


	public void setExtractTheWay(Short extractTheWay) {
		this.extractTheWay = extractTheWay;
	}


	public Short getStatus() {
		return status;
	}


	public void setStatus(Short status) {
		this.status = status;
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


	public Date getTenderAt() {
		return tenderAt;
	}


	public void setTenderAt(Date tenderAt) {
		this.tenderAt = tenderAt;
	}


	public String getPurchaseType() {
		return purchaseType;
	}


	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}


	public String getAreaName() {
		return areaName;
	}


	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}


	public String getProjectType() {
		return projectType;
	}


	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}


	public String getConstructionPro() {
		return constructionPro;
	}


	public void setConstructionPro(String constructionPro) {
		this.constructionPro = constructionPro;
	}


	public String getConstructionAddr() {
		return constructionAddr;
	}


	public void setConstructionAddr(String constructionAddr) {
		this.constructionAddr = constructionAddr;
	}


	public Date getSellBegin() {
		return sellBegin;
	}


	public void setSellBegin(Date sellBegin) {
		this.sellBegin = sellBegin;
	}


	public Date getSellEnd() {
		return sellEnd;
	}


	public void setSellEnd(Date sellEnd) {
		this.sellEnd = sellEnd;
	}


	public String getSellProvince() {
		return sellProvince;
	}


	public void setSellProvince(String sellProvince) {
		this.sellProvince = sellProvince;
	}


	public String getSellAddress() {
		return sellAddress;
	}


	public void setSellAddress(String sellAddress) {
		this.sellAddress = sellAddress;
	}


	
	public String getSellSite() {
		return sellSite;
	}


	public void setSellSite(String sellSite) {
		this.sellSite = sellSite;
	}


	public Short getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}


	public SupplierExtractProjectInfo() {
		super();
	}


	public SupplierExtractProjectInfo(String id) {
		super();
		this.id = id;
	}


	public String getPurchaseTypeName() {
		return purchaseTypeName;
	}


	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}


	public String getElseInfo() {
		return elseInfo;
	}


	public void setElseInfo(String elseInfo) {
		this.elseInfo = elseInfo;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	public String getContactNum() {
		return contactNum;
	}


	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}


	public String getExtractUser() {
		return extractUser;
	}


	public void setExtractUser(String extractUser) {
		this.extractUser = extractUser;
	}

	
	
}