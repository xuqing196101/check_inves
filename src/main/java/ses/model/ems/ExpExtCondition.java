package ses.model.ems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * @Description:抽取条件
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月29日上午10:22:22
 * @since  JDK 1.7
 */
public class ExpExtCondition implements Cloneable {
	
	
	
    public ExpExtCondition() {
		super();
	}
 
    
    
	public ExpExtCondition(String id,String s) {
		super();
		this.id = id;
	}



	public ExpExtCondition(String id, Short status) {
		super();
		this.id = id;
		this.status = status;
	}

	
	public ExpExtCondition(String projectId) {
		super();
		this.projectId = projectId;
	}
	

	
    
    private List<ExtConType> conTypes;
  
    /**
     * <pre>
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 状态1待抽取 2.已抽取
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.STATUS
     * </pre>
     */
    private Short status;

    /**
     * <pre>
     * 专家所在地区
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.ADDRESS
     * </pre>
     */
    private String address;

    /**
     * <pre>
     * 专家类型
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     */
    private String expertsTypeId;

    /**
     * <pre>
     * 开标时间
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.TENDER_TIME
     * </pre>
     */
    @NotNull(message = "开标时间不能为空")  
    private Date tenderTime;

    /**
     * <pre>
     * 响应时间
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.RESPONSE TIME
     * </pre>
     */
    private String responseTime;

    /**
     * <pre>
     * 最大年龄
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.AGE_MAX
     * </pre>
     */
    private String ageMax;

    /**
     * <pre>
     * 满足条件1,满足单个 2满足多个
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.SATISFY_CONDITION
     * </pre>
     */
    private Short satisfyCondition;

    /**
     * <pre>
     * 品目id
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.CATEGORY_ID
     * </pre>
     */
    private String categoryId;

    /**
     * <pre>
     * 专家数量
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.EXPERT_COUNT
     * </pre>
     */
    private BigDecimal expertCount;

    /**
     * <pre>
     * 专家id
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.EXPERT_ID
     * </pre>
     */
    private String expertId;

    /**
     * <pre>
     * 专家来源
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_FROM
     * </pre>
     */
    private String expertsFrom;

    /**
     * <pre>
     * 专家所在地区id
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.ADDRESS_ID
     * </pre>
     */
    private String addressId;

    /**
     * <pre>
     * 最小年龄
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.AGE_MIN
     * </pre>
     */
    private String ageMin;

    /**
     * <pre>
     * 抽取地址
     * 表字段 : T_SES_EMS_EXP_EXT_CONDITION.EXTRACT_ADDRESS
     * </pre>
     */
    private String extractAddress;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ID
     * </pre>
     *
     * @param id
     *            T_SES_EMS_EXP_EXT_CONDITION.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.PROJECT_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_SES_EMS_EXP_EXT_CONDITION.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：状态1待抽取 2.已抽取
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.STATUS
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public Short getStatus() {
        return status;
    }

    /**
     * <pre>
     * 设置：状态1待抽取 2.已抽取
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.STATUS
     * </pre>
     *
     * @param status
     *            T_SES_EMS_EXP_EXT_CONDITION.STATUS：状态1待抽取 2.已抽取
     */
    public void setStatus(Short status) {
        this.status = status;
    }

    /**
     * <pre>
     * 获取：专家所在地区
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ADDRESS
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.ADDRESS：专家所在地区
     */
    public String getAddress() {
        return address;
    }

    /**
     * <pre>
     * 设置：专家所在地区
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ADDRESS
     * </pre>
     *
     * @param address
     *            T_SES_EMS_EXP_EXT_CONDITION.ADDRESS：专家所在地区
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * <pre>
     * 获取：专家类型
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public String getExpertsTypeId() {
        return expertsTypeId;
    }

    /**
     * <pre>
     * 设置：专家类型
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_TYPE_ID
     * </pre>
     *
     * @param expertsTypeId
     *            T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_TYPE_ID：专家类型
     */
    public void setExpertsTypeId(String expertsTypeId) {
        this.expertsTypeId = expertsTypeId == null ? null : expertsTypeId.trim();
    }

    /**
     * <pre>
     * 获取：开标时间
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.TENDER_TIME
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.TENDER_TIME：开标时间
     */
    public Date getTenderTime() {
        return tenderTime;
    }

    /**
     * <pre>
     * 设置：开标时间
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.TENDER_TIME
     * </pre>
     *
     * @param tenderTime
     *            T_SES_EMS_EXP_EXT_CONDITION.TENDER_TIME：开标时间
     */
    public void setTenderTime(Date tenderTime) {
        this.tenderTime = tenderTime;
    }

    /**
     * <pre>
     * 获取：响应时间
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.RESPONSE TIME：响应时间
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * <pre>
     * 设置：响应时间
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.RESPONSE TIME
     * </pre>
     *
     * @param responseTime
     *            T_SES_EMS_EXP_EXT_CONDITION.RESPONSE TIME：响应时间
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime == null ? null : responseTime.trim();
    }

    /**
     * <pre>
     * 获取：最大年龄
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.AGE_MAX
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.AGE_MAX：最大年龄
     */
    public String getAgeMax() {
        return ageMax;
    }

    /**
     * <pre>
     * 设置：最大年龄
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.AGE_MAX
     * </pre>
     *
     * @param ageMax
     *            T_SES_EMS_EXP_EXT_CONDITION.AGE_MAX：最大年龄
     */
    public void setAgeMax(String ageMax) {
        this.ageMax = ageMax == null ? null : ageMax.trim();
    }

    /**
     * <pre>
     * 获取：满足条件1,满足单个 2满足多个
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.SATISFY_CONDITION
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.SATISFY_CONDITION：满足条件1,满足单个 2满足多个
     */
    public Short getSatisfyCondition() {
        return satisfyCondition;
    }

    /**
     * <pre>
     * 设置：满足条件1,满足单个 2满足多个
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.SATISFY_CONDITION
     * </pre>
     *
     * @param satisfyCondition
     *            T_SES_EMS_EXP_EXT_CONDITION.SATISFY_CONDITION：满足条件1,满足单个 2满足多个
     */
    public void setSatisfyCondition(Short satisfyCondition) {
        this.satisfyCondition = satisfyCondition;
    }

    /**
     * <pre>
     * 获取：品目id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.CATEGORY_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.CATEGORY_ID：品目id
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * <pre>
     * 设置：品目id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.CATEGORY_ID
     * </pre>
     *
     * @param categoryId
     *            T_SES_EMS_EXP_EXT_CONDITION.CATEGORY_ID：品目id
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    /**
     * <pre>
     * 获取：专家数量
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERT_COUNT
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.EXPERT_COUNT：专家数量
     */
    public BigDecimal getExpertCount() {
        return expertCount;
    }

    /**
     * <pre>
     * 设置：专家数量
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERT_COUNT
     * </pre>
     *
     * @param expertCount
     *            T_SES_EMS_EXP_EXT_CONDITION.EXPERT_COUNT：专家数量
     */
    public void setExpertCount(BigDecimal expertCount) {
        this.expertCount = expertCount;
    }

    /**
     * <pre>
     * 获取：专家id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERT_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.EXPERT_ID：专家id
     */
    public String getExpertId() {
        return expertId;
    }

    /**
     * <pre>
     * 设置：专家id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERT_ID
     * </pre>
     *
     * @param expertId
     *            T_SES_EMS_EXP_EXT_CONDITION.EXPERT_ID：专家id
     */
    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    /**
     * <pre>
     * 获取：专家来源
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_FROM
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_FROM：专家来源
     */
    public String getExpertsFrom() {
        return expertsFrom;
    }

    /**
     * <pre>
     * 设置：专家来源
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_FROM
     * </pre>
     *
     * @param expertsFrom
     *            T_SES_EMS_EXP_EXT_CONDITION.EXPERTS_FROM：专家来源
     */
    public void setExpertsFrom(String expertsFrom) {
        this.expertsFrom = expertsFrom == null ? null : expertsFrom.trim();
    }

    /**
     * <pre>
     * 获取：专家所在地区id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ADDRESS_ID
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.ADDRESS_ID：专家所在地区id
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * <pre>
     * 设置：专家所在地区id
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.ADDRESS_ID
     * </pre>
     *
     * @param addressId
     *            T_SES_EMS_EXP_EXT_CONDITION.ADDRESS_ID：专家所在地区id
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId == null ? null : addressId.trim();
    }

    /**
     * <pre>
     * 获取：最小年龄
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.AGE_MIN
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.AGE_MIN：最小年龄
     */
    public String getAgeMin() {
        return ageMin;
    }

    /**
     * <pre>
     * 设置：最小年龄
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.AGE_MIN
     * </pre>
     *
     * @param ageMin
     *            T_SES_EMS_EXP_EXT_CONDITION.AGE_MIN：最小年龄
     */
    public void setAgeMin(String ageMin) {
        this.ageMin = ageMin == null ? null : ageMin.trim();
    }

    /**
     * <pre>
     * 获取：抽取地址
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXTRACT_ADDRESS
     * </pre>
     *
     * @return T_SES_EMS_EXP_EXT_CONDITION.EXTRACT_ADDRESS：抽取地址
     */
    public String getExtractAddress() {
        return extractAddress;
    }

    /**
     * <pre>
     * 设置：抽取地址
     * 表字段：T_SES_EMS_EXP_EXT_CONDITION.EXTRACT_ADDRESS
     * </pre>
     *
     * @param extractAddress
     *            T_SES_EMS_EXP_EXT_CONDITION.EXTRACT_ADDRESS：抽取地址
     */
    public void setExtractAddress(String extractAddress) {
        this.extractAddress = extractAddress == null ? null : extractAddress.trim();
    }



	/**
	 * @return the conTypes
	 */
	public List<ExtConType> getConTypes() {
		return conTypes;
	}



	/**
	 * @param conTypes the conTypes to set
	 */
	public void setConTypes(List<ExtConType> conTypes) {
		this.conTypes = conTypes;
	}



    /**
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone(){
        ExpExtCondition condition = null;  
        try{  
            condition = (ExpExtCondition)super.clone();   //浅复制  
        }catch(CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
  
         return condition;
    }




}