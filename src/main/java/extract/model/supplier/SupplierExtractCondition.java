package extract.model.supplier;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class SupplierExtractCondition {

	
	private SupplierConType supplierConType;

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
     * 关联供应商
     */
    private List<SupplierExtractResult> extRelatesList;

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
     * <pre>
     * 供应商类型
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     */
    private String expertsTypeId;

    /**
     * <pre>
     * 开标时间
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     */
    private Date tenderTime;

    /**
     * <pre>
     * 响应时间
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     */
    private String responseTime;


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
     * 品目是否同时满足  1 or   2 and
     */
    private short isMulticondition;
    
    /**
     * 品目id数组形式
     */
    private String[] categoryIds;
    
    /**
     * 地址id
     */
    private String addressId;
    
    
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
    
    
    private String quaType;
    
    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_SMS_SUPPLIE_CONDITION.ADDRESS_ID
     * </pre>
     */
    private String[] addressSplit;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIE_CONDITION.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_SMS_SUPPLIE_CONDITION.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：状态1待抽取 2.已抽取
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.STATUS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public Short getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：状态1待抽取 2.已抽取
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.STATUS
     * </pre>
     *
     * @param status
     *            T_SES_SMS_SUPPLIE_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：供应商所在地区
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.ADDRESS：供应商所在地区
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：供应商所在地区
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.ADDRESS
     * </pre>
     *
     * @param address
     *            T_SES_SMS_SUPPLIE_CONDITION.ADDRESS：供应商所在地区
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * <pre>
     * 获取：专家类型
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public String getExpertsTypeId() {
        return expertsTypeId;
    }

    /**
     * <pre>
     * 设置：专家类型
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @param expertsTypeId
     *            T_SES_SMS_SUPPLIE_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public void setExpertsTypeId(String expertsTypeId) {
        this.expertsTypeId = expertsTypeId == null ? null : expertsTypeId.trim();
    }

    /**
     * <pre>
     * 获取：开标时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME：开标时间
     */
    public Date getTenderTime() {
        return tenderTime;
    }

    /**
     * <pre>
     * 设置：开标时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME
     * </pre>
     *
     * @param tenderTime
     *            T_SES_SMS_SUPPLIE_CONDITION.TENDER_TIME：开标时间
     */
    public void setTenderTime(Date tenderTime) {
        this.tenderTime = tenderTime;
    }

    /**
     * <pre>
     * 获取：响应时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME：响应时间
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * <pre>
     * 设置：响应时间
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @param responseTime
     *            T_SES_SMS_SUPPLIE_CONDITION.RESPONSE TIME：响应时间
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime == null ? null : responseTime.trim();
    }



    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM：null
     */
    public String getSupplierLevel() {
        return supplierLevel;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM
     * </pre>
     *
     * @param supplietFrom
     *            T_SES_SMS_SUPPLIE_CONDITION.SUPPLIET_FROM：null
     */
    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel == null ? null : supplierLevel.trim();
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
   
    
    /**
     * 
     * @param supplierTypeCode 抽取类型
     * @param isMulticondition 品目同时满足还是只满足一个
     * @param categoryId 品目id
     * @param levelTypeId 等级
     * @param extractNum 抽取数量
     */
    public SupplierExtractCondition(String supplierTypeCode, Short isMulticondition,
			String categoryId, String levelTypeId,Short extractNum) {
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

    /**
     * @return Returns the extRelatesList.
     */
    public List<SupplierExtractResult> getExtRelatesList() {
        return extRelatesList;
    }

    /**
     * @param extRelatesList The extRelatesList to set.
     */
    public void setExtRelatesList(List<SupplierExtractResult> extRelatesList) {
        this.extRelatesList = extRelatesList;
    }

    /**
     * @return Returns the addressSplit.
     */
    public String[] getAddressSplit() {
        return addressSplit;
    }

    /**
     * @param addressSplit The addressSplit to set.
     */
    public void setAddressSplit(String[] addressSplit) {
        this.addressSplit = addressSplit;
    }

    /**
     * @return Returns the createdAt.
     */
    public Date getCreatedAt() {
      return createdAt;
    }

    /**
     * @param createdAt The createdAt to set.
     */
    public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
    }

    /**
     * @return Returns the addressReason.
     */
    public String getAddressReason() {
      return addressReason;
    }

    /**
     * @param addressReason The addressReason to set.
     */
    public void setAddressReason(String addressReason) {
      this.addressReason = addressReason;
    }

    /**
     * @return Returns the categoryName.
     */
    public String getCategoryName() {
      return categoryName;
    }

    /**
     * @param categoryName The categoryName to set.
     */
    public void setCategoryName(String categoryName) {
      this.categoryName = categoryName;
    }

    /**
     * @return Returns the categoryId.
     */
    public String getCategoryId() {
      return categoryId;
    }

    /**
     * @param categoryId The categoryId to set.
     */
    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }

    /**
     * @return Returns the extractAddress.
     */
    public String getExtractAddress() {
      return extractAddress;
    }

    /**
     * @param extractAddress The extractAddress to set.
     */
    public void setExtractAddress(String extractAddress) {
      this.extractAddress = extractAddress;
    }

    /**
     * @return Returns the addressId.
     */
    public String getAddressId() {
      return addressId;
    }

    /**
     * @param addressId The addressId to set.
     */
    public void setAddressId(String addressId) {
      this.addressId = addressId;
    }

	public String[] getAddressIds() {
		return addressId.split(",");
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
		return categoryId.split(",");
	}

	public void setCategoryIds(String[] categoryIds) {
		this.categoryIds = categoryIds;
	}

	

	public short getIsMulticondition() {
		return isMulticondition;
	}

	public void setIsMulticondition(short isMulticondition) {
		this.isMulticondition = isMulticondition;
	}

	public String getLevelTypeId() {
		return levelTypeId;
	}

	public void setLevelTypeId(String levelTypeId) {
		this.levelTypeId = levelTypeId;
	}

	public String[] getLevelTypeIds() {
		return levelTypeId.split(",");
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
		return province.split(",");
	}

	public void setProvinces(String[] provinces) {
		this.provinces = provinces;
	}

	public String[] getSupplierTypeCodes() {
		return supplierTypeCode.split(",");
	}

	public void setSupplierTypeCodes(String[] supplierTypeCodes) {
		this.supplierTypeCodes = supplierTypeCodes;
	}

	public SupplierConType getSupplierConType() {
		return supplierConType;
	}

	public void setSupplierConType(SupplierConType supplierConType) {
		this.supplierConType = supplierConType;
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
		return quaId.split(",");
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
	
	public int getCsize(){
		return this.getCategoryIds().length;
	}

	public void setQuaType(String string) {
		
	}

	public String getQuaType() {
		return quaType;
	}
	
	
}
