package bss.model.ppms;

import java.util.Date;
/**
* @Title:BidAnnouncement 
* @Description: 招标公告实体类
* @author Peng Zhongjun
* @date 2016-10-8下午8:41:25
 */
public class BidAnnouncement {
	/**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields project : 招标项目
     */
    private Project project;
    /**
     * @Fields status : 状态
     */
    private Integer status;
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;
    /**
     * @Fields updatedAt : 更新时间
     */
    private Date updatedAt;
    /**
     * @Fields isDeleted : 是否删除
     */
    private Integer isDeleted;
    /**
     * @Fields content : 公告内容
     */
    private String content;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


}