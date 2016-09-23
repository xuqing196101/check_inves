package ses.model.sms;

import java.util.Date;
import java.util.List;

public class SupplierExtracts {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.ID
     * </pre>
     */
    private String id;

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

    /**
     * <pre>
     * 抽取条件
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTING_CONDITIONS
     * </pre>
     */
    private String extractingConditions;

    /**
     * <pre>
     * 供应商 T_SES_SMS_SUPPLIER_INFO
     * </pre>
     */
    private List<SupplierExtRelate> SupplierExtRelate;
    /**
     * <pre>
     * 供应商 T_SES_SMS_SUPPLIER_INFO
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.SUPPLIERS_ID
     * </pre>
     */
    private String suppliersId;

    /**
     * <pre>
     * 抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTS_PEOPLE
     * </pre>
     */
    private String extractsPeople;

    /**
     * <pre>
     * 监督人T_SES_OMS_PROCUREMENT_USER
     * 表字段 : T_SES_SMS_SUPPLIER_EXTRACTS.SUPERINTENDENT
     * </pre>
     */
    private String superintendent;

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
     * 获取：主键
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.ID：主键
     */
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
     * 获取：抽取条件
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTING_CONDITIONS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTING_CONDITIONS：抽取条件
     */
    public String getExtractingConditions() {
        return extractingConditions;
    }

    /**
     * <pre>
     * 设置：抽取条件
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTING_CONDITIONS
     * </pre>
     *
     * @param extractingConditions
     *            T_SES_SMS_SUPPLIER_EXTRACTS.EXTRACTING_CONDITIONS：抽取条件
     */
    public void setExtractingConditions(String extractingConditions) {
        this.extractingConditions = extractingConditions == null ? null : extractingConditions.trim();
    }

    /**
     * <pre>
     * 获取：供应商 T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.SUPPLIERS_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.SUPPLIERS_ID：供应商 T_SES_SMS_SUPPLIER_INFO
     */
    public String getSuppliersId() {
        return suppliersId;
    }

    /**
     * <pre>
     * 设置：供应商 T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.SUPPLIERS_ID
     * </pre>
     *
     * @param suppliersId
     *            T_SES_SMS_SUPPLIER_EXTRACTS.SUPPLIERS_ID：供应商 T_SES_SMS_SUPPLIER_INFO
     */
    public void setSuppliersId(String suppliersId) {
        this.suppliersId = suppliersId == null ? null : suppliersId.trim();
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
     * 获取：监督人T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.SUPERINTENDENT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_EXTRACTS.SUPERINTENDENT：监督人T_SES_OMS_PROCUREMENT_USER
     */
    public String getSuperintendent() {
        return superintendent;
    }

    /**
     * <pre>
     * 设置：监督人T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_SMS_SUPPLIER_EXTRACTS.SUPERINTENDENT
     * </pre>
     *
     * @param superintendent
     *            T_SES_SMS_SUPPLIER_EXTRACTS.SUPERINTENDENT：监督人T_SES_OMS_PROCUREMENT_USER
     */
    public void setSuperintendent(String superintendent) {
        this.superintendent = superintendent == null ? null : superintendent.trim();
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

	public List<SupplierExtRelate> getSupplierExtRelate() {
		return SupplierExtRelate;
	}

	public void setSupplierExtRelate(List<SupplierExtRelate> supplierExtRelate) {
		SupplierExtRelate = supplierExtRelate;
	}
	
	
	public SupplierExtracts() {
		super();
	}

	/**
	 * id
	 * @param id
	 */
	public SupplierExtracts(String id) {
		super();
		this.id = id;
	}

	public SupplierExtracts(String projectName, String procurementDepId,
			Date extractionTime, String extractionSites, Short extractTheWay,
			String extractingConditions,
			List<ses.model.sms.SupplierExtRelate> supplierExtRelate,
			String suppliersId, String extractsPeople, String superintendent) {
		super();
		this.projectName = projectName;
		this.procurementDepId = procurementDepId;
		this.extractionTime = extractionTime;
		this.extractionSites = extractionSites;
		this.extractTheWay = extractTheWay;
		this.extractingConditions = extractingConditions;
		SupplierExtRelate = supplierExtRelate;
		this.suppliersId = suppliersId;
		this.extractsPeople = extractsPeople;
		this.superintendent = superintendent;
	}
    
    
}