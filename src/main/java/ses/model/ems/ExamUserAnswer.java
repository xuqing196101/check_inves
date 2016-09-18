/**
 * 
 */
package ses.model.ems;

import java.util.Date;

/**
 * @Title:ExamUserAnswer
 * @Description: 用户答题记录类
 * @author ZhaoBo
 * @date 2016-9-14上午10:18:07
 */
public class ExamUserAnswer {
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
     * @Fields questionId : 题目ID
     */
	private String questionId;
	
	/**
     * @Fields content : 用户答题内容
     */
	private String content;
	
	/**
     * @Fields createdAt : 创建时间
     */
	private Date createdAt;
	
	/**
     * @Fields updatedAt : 更新时间
     */
	private Date updatedAt;
	
	/**
     * @Fields paperId : 试卷ID
     */
	private String paperId;
	
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
	
	public String getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
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

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	
	
}
