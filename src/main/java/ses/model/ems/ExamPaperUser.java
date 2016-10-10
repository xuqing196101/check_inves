/**
 * 
 */
package ses.model.ems;

import java.util.Date;


/**
 * @Title:ExamPaperUser
 * @Description: 参考人员实体类
 * @author ZhaoBo
 * @date 2016-9-13下午1:52:08
 */
public class ExamPaperUser {
	/**
	 * @Fields id : 主键ID
	 */
	private String id;
	
	/**
	 * @Fields userId : 用户ID
	 */
	private String userId;
	
	/**
	 * @Fields paperId : 试卷ID
	 */
	private String paperId;
	
	/**
	 * @Fields isDo : 记录用户是否参加了考试
	 */
	private Integer isDo;
	
	/**
	 * @Fields createdAt : 创建时间
	 */
	private Date createdAt;
	
	/**
	 * @Fields updatedAt : 更新时间
	 */
	private Date updatedAt;
	
	/**
	 * @Fields unitName : 采购人的所属单位名称
	 */
	private String unitName;
	
	/**
	 * @Fields userName : 采购人姓名
	 */
	private String userName;
	
	/**
	 * @Fields code : 试卷编号
	 */
	private String code;
	
	/**
	 * @Fields score : 考试分数
	 */
	private String score;
	
	/**
	 * @Fields card : 身份证号
	 */
	private String card;
	
	/**
	 * @Fields name : 试卷名称
	 */
	private String name;
	
	/**
	 * @Fields testTime : 考试用时
	 */
	private String testTime;
	
	/**
	 * @Fields startTime : 考试开始时间
	 */
	private Date startTime;
	
	/**
	 * @Fields isAllowRetake : 是否允许重考
	 */
	private Integer isAllowRetake;
	
	/**
	 * @Fields formatDate : 备用时间字段
	 */
	private String formatDate;
	
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

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public Integer getIsDo() {
		return isDo;
	}

	public void setIsDo(Integer isDo) {
		this.isDo = isDo;
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

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getIsAllowRetake() {
		return isAllowRetake;
	}

	public void setIsAllowRetake(Integer isAllowRetake) {
		this.isAllowRetake = isAllowRetake;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	

	
	
	
}
