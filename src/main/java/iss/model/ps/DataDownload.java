/**
 * 
 */
package iss.model.ps;

import java.util.Date;

/**
 * @Title:DataDownload
 * @Description: 资料下载实体类
 * @author ZhaoBo
 * @date 2017-1-5下午1:04:14
 */
public class DataDownload {
	/**
	 * 主键ID
	 */
	private String id;
	
	/**
	 * 资料名称
	 */
	private String name;
	
	/**
	 * 内外网状态(1内网 2 内外网)
	 */
	private Integer ipAddressType;
	
	/**
	 * 是否删除(0 未删 1 已删)
	 */
	private Integer isDeleted;
	
	/**
	 * 创建时间
	 */
	private Date createdAt;
	
	/**
	 * 更新时间
	 */
	private Date updatedAt;
	
	/**
	 * 创建人
	 */
	private String userId;
	
	/**
	 * 发布时间
	 */
	private Date publishAt;
	
	/**
	 * 状态(1暂存 2已发布 3已取消发布)
	 */
	private Integer status;
	
	/**
     * group
     */
    private String groupsUploadId;
    private String groupShowId;
    
    /**
     * id
     */
    private String groupsUpload;
    private String groupShow;
	
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

	public Integer getIpAddressType() {
		return ipAddressType;
	}

	public void setIpAddressType(Integer ipAddressType) {
		this.ipAddressType = ipAddressType;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Date getPublishAt() {
		return publishAt;
	}

	public void setPublishAt(Date publishAt) {
		this.publishAt = publishAt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getGroupsUploadId() {
		return groupsUploadId;
	}

	public void setGroupsUploadId(String groupsUploadId) {
		this.groupsUploadId = groupsUploadId;
	}

	public String getGroupShowId() {
		return groupShowId;
	}

	public void setGroupShowId(String groupShowId) {
		this.groupShowId = groupShowId;
	}

	public String getGroupsUpload() {
		return groupsUpload;
	}

	public void setGroupsUpload(String groupsUpload) {
		this.groupsUpload = groupsUpload;
	}

	public String getGroupShow() {
		return groupShow;
	}

	public void setGroupShow(String groupShow) {
		this.groupShow = groupShow;
	}
	
	
}
