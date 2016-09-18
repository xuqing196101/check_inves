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
	
	
}
