package ses.model.ems;

import java.util.Date;
import java.util.List;


public class BatchTemporary {
	private String id;
	private String expertId;
	private String batchExpertId;
	/**
     * 所属采购机构
     **/
    private String orgName;
    /**
     * 真实姓名
     */
    private String relName;
    /**
     * 性别 M 男  F 女
     */
    private String sex;
    /**
     * 专家类型
     */
    private String expertsTypeId;
    /**
     * 专家来源
     */
    private String expertsFrom;
    /**
     * 工作单位名称
     */
    private String workUnit;
    /**
     * 专家技术职称
     */
    private String professTechTitles;
    private Date auditAt; 
	private Date createdAt;
	private Date updatedAt;
	private String updateTime;
	private List<String> ids;
	
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getBatchExpertId() {
		return batchExpertId;
	}
	public void setBatchExpertId(String batchExpertId) {
		this.batchExpertId = batchExpertId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getRelName() {
		return relName;
	}
	public void setRelName(String relName) {
		this.relName = relName;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getExpertsTypeId() {
		return expertsTypeId;
	}
	public void setExpertsTypeId(String expertsTypeId) {
		this.expertsTypeId = expertsTypeId;
	}
	public String getExpertsFrom() {
		return expertsFrom;
	}
	public void setExpertsFrom(String expertsFrom) {
		this.expertsFrom = expertsFrom;
	}
	public String getWorkUnit() {
		return workUnit;
	}
	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}
	public String getProfessTechTitles() {
		return professTechTitles;
	}
	public void setProfessTechTitles(String professTechTitles) {
		this.professTechTitles = professTechTitles;
	}
	public Date getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
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
