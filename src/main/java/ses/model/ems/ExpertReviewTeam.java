package ses.model.ems;

import java.util.Date;


public class ExpertReviewTeam {
	/**主键ID*/
	private String id;
	/**用户ID*/
	private String userId;
	/**批次ID*/
	private String batchId;
	/**组ID*/
	private String groupId;
	/**创建时间*/
	private Date createdAt;
	/**修改时间*/
	private Date updatedAt;
	/** 真实姓名 */
    private String relName;
    /** 职务 */
    private String duties;
    /**单位*/
    private String orgName;
    /**用户名*/
    private String loginName;
    /**密码*/
    private String passWord;
    /** 
     * 删除标识
     * 0：未删除
     * 1：删除
     */
    private Integer isDeleted;
    
    /**
     * 顺序标识
     */
    private String indexNum;
    
	public String getIndexNum() {
		return indexNum;
	}
	public void setIndexNum(String indexNum) {
		this.indexNum = indexNum;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	public String getDuties() {
		return duties;
	}
	public void setDuties(String duties) {
		this.duties = duties;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
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
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	@Override
	public String toString() {
		return "ExpertReviewTeam [relName=" + relName + ", duties="
				+ duties + ", orgName=" + orgName + ", loginName=" + loginName + ", passWord=" + passWord
				+ ", isDeleted=" + isDeleted + "]";
	}
	
	
}
