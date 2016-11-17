package bss.model.prms.ext;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
  * <p>Title:AuditModelExt </p>
  * <p>Description: </p>去往评分页面实体封装
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年11月15日下午5:29:53
 */
public class AuditModelExt {

	/**
	 * ADUIT_QUOTA表数据
	 * */	
	
	
	/**ADUIT_QUOTA表的ID
	 * 
	 * */
	private String quotaId;
	 /**
     * @Fields projectId : 项目id
     */
    private String projectId;

    /**
     * @Fields packageId : 包id
     */
    private String packageId;

    /**
     * @Fields supplierId : 供应商id
     */
    private String supplierId;

    /**
     * @Fields scoreModelId : 评分细则与模型关联id
     */
    private String scoreModelId;

    /**
     * @Fields supplierValue : 供应商绑定值
     */
    private BigDecimal supplierValue;

    /**
     * @Fields initScore : 初始得分
     */
    private BigDecimal initScore;

    /**
     * @Fields expertValue : 专家评审值
     */
    private BigDecimal expertValue;

    /**
     * @Fields finalScore : 最终得分
     */
    private BigDecimal finalScore;

    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields updatedAt : 更新时间
     */
    private Date updatedAt;

    /**
     * @Fields isDeleted : 删除标志 0：未删除 1：删除
     */
    private Short isDeleted;

    /**
     * @Fields round : 轮次
     */
    private Integer round;
    /**
     * @Fields page : 页码
     */
    private Integer page;
    
    /**
     * SCORE_MODEL 表数据
     */
  //公用属性
  	private String markTermId;//打分项id
  	private String name;//模型名称
  	private String typeName;//类型名称 0-7   0是判断
  	private String reviewContent;//评审内容
  	private String remark;//备注
  	private String easyUnderstandContent;//通俗易懂，白话文内容
  	private String standExplain;//标准解释
  	private String status;//状态 0 未审核  1已审核 
  	//模型一相关属性        是否判断
  	private String standardScore;//标准分值
  	private String judgeContent;//判断内容
  	private String judgeNumber;//判断内容
  	//模型二相关属性   按项加减分
  	private String reviewParam;//评审参数
  	private String addSubtractTypeName;//加减分类型   如果为加分，那么高于评审基准数为0分，低于评审基准数按照规则加分；如果是减分类型，那么高于评审基准数为满分，低于评审基准数，按照规则减分
  	private String unitScore;//每单位分值
  	private String unit;//单位
  	private String reviewStandScore;//评审基准数     起始分值  逐次增加按照每单位分值(模型1) 基准分值逐次递减(模型2)评审基准数(模型7、模型8)
  	private String maxScore;//最高分
  	//模型三相关属性   评审数额最高递减
  	private String minScore;//最低分
  	//模型四相关属性   评审数额最低递增
  	private String score;//分差   每个区间的分值差，加  加多少分   减  减多少分
  	//模型五相关属性   评审数额高计算
  	//模型六相关属性   评审数额低计算
  	//模型七相关属性   评审数额低区间递增
  	private String intervalTypeName;//区间类型        0以基准值，每个区间的差额相等    1非基准值，每个区间的差额不等，是范围值
  	private String deadlineNumber;//评审参数截止数     如果为加分，低于截止数为0分，高于截止数
  	private String intervalNumber;//每个区间之间的差额，用于等额区间模型

  	/**
  	 * MARK_TERM表数据
  	 */
  	private String markTermName;//打分项名称

  	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getScoreModelId() {
		return scoreModelId;
	}

	public void setScoreModelId(String scoreModelId) {
		this.scoreModelId = scoreModelId;
	}

	public BigDecimal getSupplierValue() {
		return supplierValue;
	}

	public void setSupplierValue(BigDecimal supplierValue) {
		this.supplierValue = supplierValue;
	}

	public BigDecimal getInitScore() {
		return initScore;
	}

	public void setInitScore(BigDecimal initScore) {
		this.initScore = initScore;
	}

	public BigDecimal getExpertValue() {
		return expertValue;
	}

	public void setExpertValue(BigDecimal expertValue) {
		this.expertValue = expertValue;
	}

	public BigDecimal getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(BigDecimal finalScore) {
		this.finalScore = finalScore;
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

	public Short getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Short isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public String getMarkTermId() {
		return markTermId;
	}

	public void setMarkTermId(String markTermId) {
		this.markTermId = markTermId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEasyUnderstandContent() {
		return easyUnderstandContent;
	}

	public void setEasyUnderstandContent(String easyUnderstandContent) {
		this.easyUnderstandContent = easyUnderstandContent;
	}

	public String getStandExplain() {
		return standExplain;
	}

	public void setStandExplain(String standExplain) {
		this.standExplain = standExplain;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(String standardScore) {
		this.standardScore = standardScore;
	}

	public String getJudgeContent() {
		return judgeContent;
	}

	public void setJudgeContent(String judgeContent) {
		this.judgeContent = judgeContent;
	}

	public String getJudgeNumber() {
		return judgeNumber;
	}

	public void setJudgeNumber(String judgeNumber) {
		this.judgeNumber = judgeNumber;
	}

	public String getReviewParam() {
		return reviewParam;
	}

	public void setReviewParam(String reviewParam) {
		this.reviewParam = reviewParam;
	}

	public String getAddSubtractTypeName() {
		return addSubtractTypeName;
	}

	public void setAddSubtractTypeName(String addSubtractTypeName) {
		this.addSubtractTypeName = addSubtractTypeName;
	}

	public String getUnitScore() {
		return unitScore;
	}

	public void setUnitScore(String unitScore) {
		this.unitScore = unitScore;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReviewStandScore() {
		return reviewStandScore;
	}

	public void setReviewStandScore(String reviewStandScore) {
		this.reviewStandScore = reviewStandScore;
	}

	public String getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}

	public String getMinScore() {
		return minScore;
	}

	public void setMinScore(String minScore) {
		this.minScore = minScore;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getIntervalTypeName() {
		return intervalTypeName;
	}

	public void setIntervalTypeName(String intervalTypeName) {
		this.intervalTypeName = intervalTypeName;
	}

	public String getDeadlineNumber() {
		return deadlineNumber;
	}

	public void setDeadlineNumber(String deadlineNumber) {
		this.deadlineNumber = deadlineNumber;
	}

	public String getIntervalNumber() {
		return intervalNumber;
	}

	public void setIntervalNumber(String intervalNumber) {
		this.intervalNumber = intervalNumber;
	}

	public String getMarkTermName() {
		return markTermName;
	}

	public void setMarkTermName(String markTermName) {
		this.markTermName = markTermName;
	}
  	
}
