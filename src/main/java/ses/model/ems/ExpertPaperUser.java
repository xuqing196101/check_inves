/**
 * 
 */
package ses.model.ems;

import java.util.Date;

/**
 * @Title:ExpertPaperUser
 * @Description: 专家参考人员表
 * @author ZhaoBo
 * @date 2016-11-17上午9:27:46
 */
public class ExpertPaperUser {
	/**
	 * @Fields id : 主键ID
	 */
	private String id;
	
	/**
	 * @Fields userId : 用户ID
	 */
	private String userId;
	
	/**
	 * @Fields isDo : 是否参加考试(0未考1已考)
	 */
	private Integer isDo;
	
	/**
	 * @Fields isPass : 是否通过(0未通过1已通过)
	 */
	private Integer isPass;
	
	/**
	 * @Fields year : 考试年份
	 */
	private Integer year;
	
	/**
	 * @Fields createdAt : 创建时间
	 */
	private Date createdAt;
	
	/**
	 * @Fields updatedAt : 更新时间
	 */
	private Date updatedAt;
	
	/**
	 * @Fields relName : 用户的真实姓名
	 */
	private String relName;
	
	/**
	 * @Fields ruleId : 规则ID
	 */
	private String ruleId;
	
	/**
	 * @Fields userType : 专家类型(1技术2商务3法律)
	 */
	private Integer userType;
	
	/**
	 * @Fields idNumber : 证件号
	 */
	private String idNumber;

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

	public Integer getIsDo() {
		return isDo;
	}

	public void setIsDo(Integer isDo) {
		this.isDo = isDo;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
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

	public String getRelName() {
		return relName;
	}

	public void setRelName(String relName) {
		this.relName = relName;
	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
}
