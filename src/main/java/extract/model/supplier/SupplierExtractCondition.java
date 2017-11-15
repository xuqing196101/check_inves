package extract.model.supplier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

public class SupplierExtractCondition {

	private String recordId;

	/**
	 * 保密要求
	 */
	private String isHavingConCert;

	/**
	 * 企业性质
	 */
	private String businessNature;

	/**
	 * 工程资质
	 */

	private String[] quaIds;

	private String quaId;

	/**
	 * 排除供应商
	 */
	private List<String> supplierIds;
	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 项目id
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
	 * </pre>
	 */
	private String projectId;

	/**
	 * <pre>
	 * 状态1待抽取 2.已抽取
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.STATUS
	 * </pre>
	 */
	private Short status;

	/**
	 * <pre>
	 * 抽取数量
	 * 表字段 :
	 * </pre>
	 */
	@NotNull
	private Short extractNum;

	/**
	 * <pre>
	 * 供应商所在地区
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
	 * </pre>
	 */
	private String address;

	/**
	 * 品目同时满足时 sql查询用
	 */
	private int csize;

	
	
	private int quaSize;

	/**
	 * 业务承揽范围
	 */
	private String businessScope;
	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.SUPPLIER_LEVEL
	 * </pre>
	 */
	private String supplierLevel;

	private String[] supplierTypeCodes;

	/**
	 * <pre>
	 * 抽取地点
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
	 * </pre>
	 */
	private String extractAddress;

	/**
	 * 一级省市
	 */
	private String province;

	private String[] provinces;

	@NotEmpty
	private String areaName;

	/**
	 * 自动抽取用字段，当前需要抽取的人数，判断是否补抽
	 */
	private String currentExtractNum;
	
	/**
	 * 境外分支
	 */
	private String overseasBranch;
	/**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 限制地区理由
	 */
	private String addressReason;

	/**
	 * 抽取供应商类型code
	 */
	private String supplierTypeCode;

	/**
	 * 品目
	 */
	private String categoryName;

	/**
	 * 品目id
	 */
	private String categoryId;

	/**
	 * 品目父节点id
	 */
	private String parentId;

	/**
	 * 品目是否同时满足 1 or 2 and
	 */
	private Short isMulticondition;

	/**
	 * 品目id数组形式
	 */
	private String[] categoryIds;

	/**
	 * 地址id
	 */
	private String addressId;

	private Short isDelete;

	/**
	 * 抽取级别id
	 */
	private String levelTypeId;

	/**
	 * 抽取等级id 数组形式
	 */
	private String[] levelTypeIds;

	/**
	 * 数组形式地址信息
	 */
	private String[] addressIds;

	/**
	 * 资质类型
	 */
	private String quaType;

	private Date updatedAt;

	/**
	 * <pre>
	 * 抽取地点
	 * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
	 * </pre>
	 */
	private String[] addressSplit;
	
	/**
	 * 抽取类别GOODS时，使用
	 */
	private String salesLevelTypeId;
	private String[] salesLevelTypeIds;
	
	private List<String> supplierItemId = new ArrayList<>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId == null ? null : projectId.trim();
	}

	
	public Short getStatus() {
		return status;
	}

	
	public void setStatus(Short status) {
		this.status = status;
	}

	
	public String getAddress() {
		return address;
	}

	
	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	
	public String getSupplierLevel() {
		return supplierLevel;
	}

	public void setSupplierLevel(String supplierLevel) {
		this.supplierLevel = supplierLevel == null ? null : supplierLevel
				.trim();
	}

	private String expertsFrom;

	public String getExpertsFrom() {
		return expertsFrom;
	}

	public void setExpertsFrom(String expertsFrom) {
		this.expertsFrom = expertsFrom;
	}

	public SupplierExtractCondition() {
		super();
	}

	public SupplierExtractCondition(String supplierTypeCode,
			Short isMulticondition, String categoryId, String levelTypeId,
			Short extractNum) {
		super();
		this.supplierTypeCode = supplierTypeCode;
		this.isMulticondition = isMulticondition;
		this.categoryId = categoryId;
		this.levelTypeId = levelTypeId;
		this.extractNum = extractNum;
	}

	public SupplierExtractCondition(String id, String s) {
		super();
		this.id = id;
	}

	public SupplierExtractCondition(String id, Short status) {
		super();
		this.id = id;
		this.status = status;
	}

	public SupplierExtractCondition(String projectId) {
		super();
		this.projectId = projectId;
	}

	public String[] getAddressSplit() {
		return addressSplit;
	}

	public void setAddressSplit(String[] addressSplit) {
		this.addressSplit = addressSplit;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getAddressReason() {
		return addressReason;
	}

	public void setAddressReason(String addressReason) {
		this.addressReason = addressReason;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getExtractAddress() {
		return extractAddress;
	}

	public void setExtractAddress(String extractAddress) {
		this.extractAddress = extractAddress;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}

	public String[] getAddressIds() {
		return StringUtils.isNotBlank(addressId) ? addressId.split(",") : null;
	}

	public void setAddressIds(String[] addressIds) {
		this.addressIds = addressIds;
	}

	public String getSupplierTypeCode() {
		return supplierTypeCode;
	}

	public void setSupplierTypeCode(String supplierTypeCode) {
		this.supplierTypeCode = supplierTypeCode;
	}

	public String[] getCategoryIds() {
		return StringUtils.isNotBlank(categoryId) ? categoryId.split(",")
				: null;
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Short getIsMulticondition() {
		return isMulticondition;
	}

	public void setIsMulticondition(Short isMulticondition) {
		this.isMulticondition = isMulticondition;
	}

	public String getLevelTypeId() {
		return levelTypeId;
	}

	public void setLevelTypeId(String levelTypeId) {
		this.levelTypeId = levelTypeId;
	}

	public String[] getLevelTypeIds() {
		return StringUtils.isNotBlank(levelTypeId) ? levelTypeId.split(",")
				: null;
	}

	public void setLevelTypeIds(String[] levelTypeIds) {
		this.levelTypeIds = levelTypeIds;
	}

	public Short getExtractNum() {
		return extractNum;
	}

	public void setExtractNum(Short extractNum) {
		this.extractNum = extractNum;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public List<String> getSupplierIds() {
		return supplierIds;
	}

	public void setSupplierIds(List<String> supplierIds) {
		this.supplierIds = supplierIds;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String[] getProvinces() {
		return StringUtils.isNotBlank(province) ? province.split(",") : null;
	}

	public void setProvinces(String[] provinces) {
		this.provinces = provinces;
	}

	public String[] getSupplierTypeCodes() {
		return StringUtils.isNotBlank(supplierTypeCode) ? supplierTypeCode
				.split(",") : null;
	}

	public void setSupplierTypeCodes(String[] supplierTypeCodes) {
		this.supplierTypeCodes = supplierTypeCodes;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getIsHavingConCert() {
		return isHavingConCert;
	}

	public void setIsHavingConCert(String isHavingConCert) {
		this.isHavingConCert = isHavingConCert;
	}

	public String getOverseasBranch() {
		return overseasBranch;
	}

	public void setOverseasBranch(String overseasBranch) {
		this.overseasBranch = overseasBranch;
	}

	public String getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public String[] getQuaIds() {
		return StringUtils.isNotBlank(quaId) ? quaId.split(",") : null;
	}

	public void setQuaIds(String[] quaIds) {
		this.quaIds = quaIds;
	}

	public String getQuaId() {
		return quaId;
	}

	public void setQuaId(String quaId) {
		this.quaId = quaId;
	}

	public void setQuaType(String quaType) {
		this.quaType = quaType;
	}

	public String getQuaType() {
		return quaType;
	}

	public Short getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Short isDelete) {
		this.isDelete = isDelete;
	}

	public Date getUpdatedAt() {
		return this.updatedAt == null ? new Date() : this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getQuaSize() {
		return StringUtils.isBlank(quaId) ? quaSize : quaId.split(",").length;
	}

	public void setQuaSize(int quaSize) {
		this.quaSize = quaSize;
	}

	public void setCsize(int size) {
		this.csize = size;
	}

	public int getCsize() {
		return this.csize;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCurrentExtractNum() {
		return currentExtractNum;
	}

	public void setCurrentExtractNum(String currentExtractNum) {
		this.currentExtractNum = currentExtractNum;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	public String getSalesLevelTypeId() {
		return salesLevelTypeId;
	}

	public void setSalesLevelTypeId(String salesLevelTypeId) {
		this.salesLevelTypeId = salesLevelTypeId;
	}

	public String[] getSalesLevelTypeIds() {
		return StringUtils.isNotBlank(salesLevelTypeId)?salesLevelTypeId.split(","):null;
	}

	public void setSalesLevelTypeIds(String[] salesLevelTypeIds) {
		this.salesLevelTypeIds = salesLevelTypeIds;
	}

	public List<String> getSupplierItemId() {
		return supplierItemId;
	}

	public void setSupplierItemId(List<String> supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

	
	
	
}
