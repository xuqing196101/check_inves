package ses.model.ems;

import java.util.Date;
import java.util.List;

public class ExpertBatchDetails {
	
	/**批次详情表ID*/
	private String id;
	/**专家ID*/
	private String expertId;
	/**批次编号*/
	private String batchNumber;
	/**批次详情编号*/
	private String batchDetailsNumber;
	/**专家编号*/
	private String count;
	/**批次名称*/
	private String batchName;
	/**所属组ID*/
	private String groupId;
	/**组名*/
	private String groupName;
	/**创建时间*/
	private Date createdAt;
	/**修改时间*/
	private Date updatedAt;
	/**所属批次ID*/
	private String batchId;
	/**真实姓名*/
	private String realName;
	/**性别 */
	private String gender;
	/**工作单位*/
	private String workUnit;
	/**专业职称*/
	private String professTechTitles;
	/**所属采购机构*/
	private String orgName;
	/**专家类型*/
	private String expertsTypeId;
	/**专家类别*/
	private String expertsFrom;
	/**审核人*/
	private String auditor;
	/**审核状态*/
	private String status;
	/**标识审核中状态*/
	private String auditTemporary;
	/**提交复审时间*/
	private Date updateTime;
	/**复审时间*/
	private Date auditAt;
	private List<String> ids;
	/**是否下载附件(0否，1是)*/
	private Integer isDownload;
	/**是否复审（1是）结束*/
	private Integer isReviewEnd;
	/**排序*/
	private String sort;
	
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public String getAuditTemporary() {
		return auditTemporary;
	}
	public void setAuditTemporary(String auditTemporary) {
		this.auditTemporary = auditTemporary;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getAuditAt() {
		return auditAt;
	}
	public void setAuditAt(Date auditAt) {
		this.auditAt = auditAt;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getBatchDetailsNumber() {
		return batchDetailsNumber;
	}
	public void setBatchDetailsNumber(String batchDetailsNumber) {
		this.batchDetailsNumber = batchDetailsNumber;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public Integer getIsDownload() {
		return isDownload;
	}
	public void setIsDownload(Integer isDownload) {
		this.isDownload = isDownload;
	}
	public Integer getIsReviewEnd() {
		return isReviewEnd;
	}
	public void setIsReviewEnd(Integer isReviewEnd) {
		this.isReviewEnd = isReviewEnd;
	}
	
	
	
}
