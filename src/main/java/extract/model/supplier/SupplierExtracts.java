package extract.model.supplier;

import java.util.Date;

import ses.model.bms.User;

import com.sun.jmx.snmp.UserAcl;

public class SupplierExtracts {
	
	
    public SupplierExtracts() {
		super();
	}

	public SupplierExtracts(String id) {
		super();
		this.id = id;
	}

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.ID
	 * </pre>
	 */
	private String id;
	
	private String packageId;
	
	private String packageName;
	
	private String packageNumber;
	
	private String projectNumber;

    /**
     * <pre>
     * 项目名称
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME
     * </pre>
     */
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
     * 抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE
     * </pre>
     */
    private String extractsPeople;

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
    
    
    /**
     * <pre>
     * 开标日期格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT
     * </pre>
     */
    private Date tenderAt;

    /**
     * <pre>
     * 项目代码
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE
     * </pre>
     */
    private String projectCode;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * 操作人员
     */
    private User perpleUser;
    
    public User getPerpleUser() {
		return perpleUser;
	}

	public void setPerpleUser(User perpleUser) {
		this.perpleUser = perpleUser;
	}

    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_EXTRACTS.ID：主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 采购方式
     */
    private String purchaseType;
    
    /**
     * 采购方式name
     */
    private String purchaseTypeName;
    
    /**
     * <pre>
     * 获取：项目名称
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME：项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <pre>
     * 设置：项目名称
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME
     * </pre>
     *
     * @param projectName
     *            T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_NAME：项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * <pre>
     * 获取：采购机构T_SES_OMS_PROCUREMENT_DEP
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROCUREMENT_DEP_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.PROCUREMENT_DEP_ID：采购机构T_SES_OMS_PROCUREMENT_DEP
     */
    public String getProcurementDepId() {
        return procurementDepId;
    }

    /**
     * <pre>
     * 设置：采购机构T_SES_OMS_PROCUREMENT_DEP
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROCUREMENT_DEP_ID
     * </pre>
     *
     * @param procurementDepId
     *            T_SES_SMS_SUPPLIER_EXTRACTS.PROCUREMENT_DEP_ID：采购机构T_SES_OMS_PROCUREMENT_DEP
     */
    public void setProcurementDepId(String procurementDepId) {
        this.procurementDepId = procurementDepId == null ? null : procurementDepId.trim();
    }

    /**
     * <pre>
     * 获取：抽取时间
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_TIME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_TIME：抽取时间
     */
    public Date getExtractionTime() {
        return extractionTime;
    }

    /**
     * <pre>
     * 设置：抽取时间
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_TIME
     * </pre>
     *
     * @param extractionTime
     *            T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_TIME：抽取时间
     */
    public void setExtractionTime(Date extractionTime) {
        this.extractionTime = extractionTime;
    }

    /**
     * <pre>
     * 获取：抽取地点
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_SITES
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_SITES：抽取地点
     */
    public String getExtractionSites() {
        return extractionSites;
    }

    /**
     * <pre>
     * 设置：抽取地点
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_SITES
     * </pre>
     *
     * @param extractionSites
     *            T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTION_SITES：抽取地点
     */
    public void setExtractionSites(String extractionSites) {
        this.extractionSites = extractionSites == null ? null : extractionSites.trim();
    }

    /**
     * <pre>
     * 获取：抽取方式 0语音抽取 1人工抽取
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACT_THE_WAY
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACT_THE_WAY：抽取方式 0语音抽取 1人工抽取
     */
    public Short getExtractTheWay() {
        return extractTheWay;
    }

    /**
     * <pre>
     * 设置：抽取方式 0语音抽取 1人工抽取
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACT_THE_WAY
     * </pre>
     *
     * @param extractTheWay
     *            T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACT_THE_WAY：抽取方式 0语音抽取 1人工抽取
     */
    public void setExtractTheWay(Short extractTheWay) {
        this.extractTheWay = extractTheWay;
    }

    /**
     * <pre>
     * 获取：抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE：抽取人员 T_SES_OMS_PROCUREMENT_USER
     */
    public String getExtractsPeople() {
        return extractsPeople;
    }

    /**
     * <pre>
     * 设置：抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE
     * </pre>
     *
     * @param extractsPeople
     *            T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE：抽取人员 T_SES_OMS_PROCUREMENT_USER
     */
    public void setExtractsPeople(String extractsPeople) {
        this.extractsPeople = extractsPeople == null ? null : extractsPeople.trim();
    }

    /**
     * <pre>
     * 获取：创建时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.CREATED_AT：创建时间格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_EXTRACTS.CREATED_AT：创建时间格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT：更新时间格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_EXTRACTS.UPDATED_AT：更新时间格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：项目代码
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE：项目代码
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * <pre>
     * 设置：项目代码
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE
     * </pre>
     *
     * @param projectCode
     *            T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_CODE：项目代码
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_SMS_SUPPLIER_EXTRACTS.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseTypeId(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getPurchaseTypeName() {
		return purchaseTypeName;
	}

	public void setPurchaseTypeName(String purchaseTypeName) {
		this.purchaseTypeName = purchaseTypeName;
	}


	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public Date getTenderAt() {
		return tenderAt;
	}

	public void setTenderAt(Date tenderAt) {
		this.tenderAt = tenderAt;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}
	
	
}