/**
 * 
 */
package bss.model.dms;

import java.util.Date;

/**
 * @Title:archiveBorrow
 * @Description: 档案借阅实体类
 * @author ZhaoBo
 * @date 2016-10-26下午4:28:37
 */
public class ArchiveBorrow {
	/**
     * @Fields id : 主键
     */
	private String id;
	
	/**
     * @Fields userId : 用户ID
     */
	private String userId;
	
	/**
     * @Fields authorizeTime : 授权时限
     */
	private String authorizeTime;
	
	/**
     * @Fields createdAt : 创建时间
     */
	private Date createdAt;
	
	/**
     * @Fields updatedAt : 更新时间
     */
	private Date updatedAt; 
	
	/**
	 * @Fields updatedAt : 更新时间
	 */
	private String archiveId;
	
	/**
	 * @Fields name : 扫描件名称
	 */
	private String name;
	
	/**
	 * @Fields path : 扫描件路径
	 */
	private String path;
	
	/**
	 * @Fields isDeleted : 是否删除
	 */
	private Integer isDeleted;
	
	/**
	 * @Fields attachmentId : 附件ID
	 */
	private String attachmentId;
	
	/**
	 * @Fields status : 显示格式
	 */
	private Integer status;
	
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
	
	public String getAuthorizeTime() {
		return authorizeTime;
	}

	public void setAuthorizeTime(String authorizeTime) {
		this.authorizeTime = authorizeTime;
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

	public String getArchiveId() {
		return archiveId;
	}

	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
	
}
