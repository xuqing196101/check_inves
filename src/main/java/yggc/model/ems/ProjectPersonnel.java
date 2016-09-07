package yggc.model.ems;

import java.util.Date;
/**
 * 
* <p>Title:ProjectPersonnel </p>
* <p>Description: 项目抽取人员表</p>
* <p>Company: yggc </p> 
* @author Q
* @date 2016-9-2下午2:44:51
 */
public class ProjectPersonnel {
	private String id;  //主键
	private Integer ProjectId;  //项目Id
	private Integer ExpeptExtractRecordId;  //专家抽取记录Id
	private Integer ExpertId;  //专家Id
	private Date CreatedAt;  //创建时间
	private Date updateAt;  //更新时间
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return ProjectId;
	}
	public void setProjectId(Integer projectId) {
		ProjectId = projectId;
	}
	public Integer getExpeptExtractRecordId() {
		return ExpeptExtractRecordId;
	}
	public void setExpeptExtractRecordId(Integer expeptExtractRecordId) {
		ExpeptExtractRecordId = expeptExtractRecordId;
	}
	public Integer getExpertId() {
		return ExpertId;
	}
	public void setExpertId(Integer expertId) {
		ExpertId = expertId;
	}
	public Date getCreatedAt() {
		return CreatedAt;
	}
	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
}
