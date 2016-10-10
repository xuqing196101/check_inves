package bss.model.ppms;

import java.util.Date;
/**

* @Title:BidDocument 
* @Description: 招标文件实体类
* @author Peng Zhongjun
* @date 2016-10-8下午8:54:34
 */
public class BidDocument {
	/**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields project : 招标项目
     */
    private Project project;
    /**
     * @Fields name : 招标文件名称
     */
    private String name;
    /**
     * @Fields saleStartAt : 发售开始时间
     */
    private Date saleStartAt;
    /**
     * @Fields saleEndAt : 发售截止时间
     */
    private Date saleEndAt;
    /**
     * @Fields saleAddress :发售地点
     */
    private String saleAddress;
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
     * @Fields content : 文件内容
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Date getSaleStartAt() {
		return saleStartAt;
	}
	public void setSaleStartAt(Date saleStartAt) {
		this.saleStartAt = saleStartAt;
	}
	public Date getSaleEndAt() {
		return saleEndAt;
	}
	public void setSaleEndAt(Date saleEndAt) {
		this.saleEndAt = saleEndAt;
	}
	public String getSaleAddress() {
		return saleAddress;
	}
	public void setSaleAddress(String saleAddress) {
		this.saleAddress = saleAddress;
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