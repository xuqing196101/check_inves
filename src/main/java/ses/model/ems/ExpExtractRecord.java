package ses.model.ems;

import java.util.Date;

public class ExpExtractRecord {
	
	
    public ExpExtractRecord() {
		super();
	}
    

	public ExpExtractRecord(String id) {
		super();
		this.id = id;
	}


	/**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目名称
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_NAME
     * </pre>
     */
    private String projectName;

    /**
     * <pre>
     * 项目代码
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_CODE
     * </pre>
     */
    private String projectCode;

    /**
     * <pre>
     * 采购机构
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.PROCUREMENT_DEP_ID
     * </pre>
     */
    private String procurementDepId;

    /**
     * <pre>
     * 抽取时间
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_TIME
     * </pre>
     */
    private Date extractionTime;

    /**
     * <pre>
     * 抽取地点
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_SITES
     * </pre>
     */
    private String extractionSites;

    /**
     * <pre>
     * 抽取方式
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACT_THE_WAY
     * </pre>
     */
    private Short extractTheWay;

    /**
     * <pre>
     * 抽取数量
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_THE_NUMBER
     * </pre>
     */
    private String extractsTheNumber;

    /**
     * <pre>
     * 抽取条件
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTING_CONDITIONS
     * </pre>
     */
    private String extractingConditions;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * UPDATED_AT	更新时间
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段 : T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_PEOPLE
     * </pre>
     */
    private String extractsPeople;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_EXP_EXTRACT_RECORD.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目名称
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_NAME
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_NAME：项目名称
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * <pre>
     * 设置：项目名称
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_NAME
     * </pre>
     *
     * @param projectName
     *            T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_NAME：项目名称
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    /**
     * <pre>
     * 获取：项目代码
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_CODE
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_CODE：项目代码
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * <pre>
     * 设置：项目代码
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_CODE
     * </pre>
     *
     * @param projectCode
     *            T_SES_EMS_EXP_EXTRACT_RECORD.PROJECT_CODE：项目代码
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    /**
     * <pre>
     * 获取：采购机构
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROCUREMENT_DEP_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.PROCUREMENT_DEP_ID：采购机构
     */
    public String getProcurementDepId() {
        return procurementDepId;
    }

    /**
     * <pre>
     * 设置：采购机构
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.PROCUREMENT_DEP_ID
     * </pre>
     *
     * @param procurementDepId
     *            T_SES_EMS_EXP_EXTRACT_RECORD.PROCUREMENT_DEP_ID：采购机构
     */
    public void setProcurementDepId(String procurementDepId) {
        this.procurementDepId = procurementDepId == null ? null : procurementDepId.trim();
    }

    /**
     * <pre>
     * 获取：抽取时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_TIME
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_TIME：抽取时间
     */
    public Date getExtractionTime() {
        return extractionTime;
    }

    /**
     * <pre>
     * 设置：抽取时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_TIME
     * </pre>
     *
     * @param extractionTime
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_TIME：抽取时间
     */
    public void setExtractionTime(Date extractionTime) {
        this.extractionTime = extractionTime;
    }

    /**
     * <pre>
     * 获取：抽取地点
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_SITES
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_SITES：抽取地点
     */
    public String getExtractionSites() {
        return extractionSites;
    }

    /**
     * <pre>
     * 设置：抽取地点
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_SITES
     * </pre>
     *
     * @param extractionSites
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTION_SITES：抽取地点
     */
    public void setExtractionSites(String extractionSites) {
        this.extractionSites = extractionSites == null ? null : extractionSites.trim();
    }

    /**
     * <pre>
     * 获取：抽取方式
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACT_THE_WAY
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACT_THE_WAY：抽取方式
     */
    public Short getExtractTheWay() {
        return extractTheWay;
    }

    /**
     * <pre>
     * 设置：抽取方式
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACT_THE_WAY
     * </pre>
     *
     * @param extractTheWay
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACT_THE_WAY：抽取方式
     */
    public void setExtractTheWay(Short extractTheWay) {
        this.extractTheWay = extractTheWay;
    }

    /**
     * <pre>
     * 获取：抽取数量
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_THE_NUMBER
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_THE_NUMBER：抽取数量
     */
    public String getExtractsTheNumber() {
        return extractsTheNumber;
    }

    /**
     * <pre>
     * 设置：抽取数量
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_THE_NUMBER
     * </pre>
     *
     * @param extractsTheNumber
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_THE_NUMBER：抽取数量
     */
    public void setExtractsTheNumber(String extractsTheNumber) {
        this.extractsTheNumber = extractsTheNumber == null ? null : extractsTheNumber.trim();
    }

    /**
     * <pre>
     * 获取：抽取条件
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTING_CONDITIONS
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTING_CONDITIONS：抽取条件
     */
    public String getExtractingConditions() {
        return extractingConditions;
    }

    /**
     * <pre>
     * 设置：抽取条件
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTING_CONDITIONS
     * </pre>
     *
     * @param extractingConditions
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTING_CONDITIONS：抽取条件
     */
    public void setExtractingConditions(String extractingConditions) {
        this.extractingConditions = extractingConditions == null ? null : extractingConditions.trim();
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.CREATED_AT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_EMS_EXP_EXTRACT_RECORD.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：UPDATED_AT	更新时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.UPDATED_AT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.UPDATED_AT：UPDATED_AT	更新时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：UPDATED_AT	更新时间
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_EMS_EXP_EXTRACT_RECORD.UPDATED_AT：UPDATED_AT	更新时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_PEOPLE
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_PEOPLE：抽取人员 T_SES_OMS_PROCUREMENT_USER
     */
    public String getExtractsPeople() {
        return extractsPeople;
    }

    /**
     * <pre>
     * 设置：抽取人员 T_SES_OMS_PROCUREMENT_USER
     * 表字段：T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_PEOPLE
     * </pre>
     *
     * @param extractsPeople
     *            T_SES_EMS_EXP_EXTRACT_RECORD.EXTRACTS_PEOPLE：抽取人员 T_SES_OMS_PROCUREMENT_USER
     */
    public void setExtractsPeople(String extractsPeople) {
        this.extractsPeople = extractsPeople == null ? null : extractsPeople.trim();
    }
}