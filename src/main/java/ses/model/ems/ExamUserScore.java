package ses.model.ems;

import java.util.Date;

import ses.model.bms.User;


/**
* @Title:ExamUserScore 
* @Description:用户成绩类
* @author ZhaoBo
* @date 2016-9-7下午6:16:03
 */
public class ExamUserScore {
	/**
     * @Fields id : 主键ID
     */
    private String id;
    
    /**
     * @Fields userId : 用户ID
     */
    private String userId;
    
    /**
     * @Fields userType : 用户类型
     */
    private Integer userType;
    
    /**
     * @Fields score : 用户得分
     */
    private String score;
    
    /**
     * @Fields status : 用户考试状态
     */
    private String status;
    
    /**
     * @Fields paperId : 用户做的试卷(针对采购人)
     */
    private String paperId;
    
    /**
     * @Fields createdAt : 创建日期
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt : 更新日期
     */
    private Date updatedAt;
    
    /**
     * @Fields testDate : 考试日期
     */
    private Date testDate;
    
    /**
     * @Fields expert : 和专家表一对一关联
     */
    private Expert expert;
    
    /**
     * @Fields relName : 用户真实姓名
     */
	private String relName;
    
	/**
     * @Fields userDuty : 备用专家类别
     */
	private String userDuty;
	
	/**
     * @Fields formatDate : 备用日期
     */
    private String formatDate;
    
    /**
     *@Fields user : 关联USER表 
     */
    private User user;
    
    /**
     *@Fields code : 试卷编号
     */
    private String code;
    
    /**
     *@Fields card : 身份证号
     */
    private String card;
    
    /**
     *@Fields idType : 身份证号
     */
    private String idType;
    
    /**
     *@Fields idNumber : 身份证号
     */
    private String idNumber;
    
    /**
     *@Fields isMax : 是否是该用户的最高分数
     */
    private Integer isMax;
    
    /**
	 * @Fields year : 考试年份
	 */
	private Integer year;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
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

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getUserDuty() {
		return userDuty;
	}

	public void setUserDuty(String userDuty) {
		this.userDuty = userDuty;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getIsMax() {
		return isMax;
	}

	public void setIsMax(Integer isMax) {
		this.isMax = isMax;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}