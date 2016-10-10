/**
 * 
 */
package bss.model.ppms;

import java.util.List;

import bss.model.pms.PurchaseRequired;

/**
 * @Title:Package
 * @Description: 分包实体类
 * @author ZhaoBo
 * @date 2016-10-9下午1:57:22
 */
public class Packages {
	private String id;
	
	private String name;
	
	private String projectId;
	
	private Integer isDeleted;
	
	private List<ProjectDetail> projectDetails;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ProjectDetail> getProjectDetails() {
		return projectDetails;
	}

	public void setProjectDetails(List<ProjectDetail> projectDetails) {
		this.projectDetails = projectDetails;
	}

	
	
	
	
}
