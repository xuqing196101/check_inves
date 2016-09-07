package yggc.model.ems;

import java.util.Date;
/**
 * 
* <p>Title:ProjectSupervisor </p>
* <p>Description:项目监督人员表 </p>
* <p>Company: yggc </p> 
* @author Q
* @date 2016-9-2下午2:51:00
 */
public class ProjectSupervisor {
	private String id;  //主键
	private Integer ProjectId;  //项目Id
	private Integer ExpertExtractRecordId;  //专家抽取记录Id
	private Integer SupviseId;  //监督人员Id
	private Date CreateAt;  //创建时间
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
	public Integer getExpertExtractRecordId() {
		return ExpertExtractRecordId;
	}
	public void setExpertExtractRecordId(Integer expertExtractRecordId) {
		ExpertExtractRecordId = expertExtractRecordId;
	}
	public Integer getSupviseId() {
		return SupviseId;
	}
	public void setSupviseId(Integer supviseId) {
		SupviseId = supviseId;
	}
	public Date getCreateAt() {
		return CreateAt;
	}
	public void setCreateAt(Date createAt) {
		CreateAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	
}
