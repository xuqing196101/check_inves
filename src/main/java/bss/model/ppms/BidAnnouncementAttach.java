package bss.model.ppms;

import java.util.Date;
/**

* @Title:BidAnnouncementAttach 
* @Description: 招标公告附件实体类
* @author Peng Zhongjun
* @date 2016-10-8下午8:46:19
 */
public class BidAnnouncementAttach {
	/**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields bidAnnouncement : 所属招标公告
     */
    private BidAnnouncement bidAnnouncement;
    /**
     * @Fields fileName : 文件名
     */
    private String fileName;
    /**
     * @Fields contentType : 文件类型
     */
    private String contentType;
    /**
     * @Fields fileSize : 文件大小
     */
    private Float fileSize;
    /**
     * @Fields attachmentPath : 文件路径
     */
    private String attachmentPath;
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;
    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;
    /**
     * @Fields isDeleted : 是否删除
     */
    private Integer isDeleted;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BidAnnouncement getBidAnnouncement() {
		return bidAnnouncement;
	}
	public void setBidAnnouncement(BidAnnouncement bidAnnouncement) {
		this.bidAnnouncement = bidAnnouncement;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public Float getFileSize() {
		return fileSize;
	}
	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}
	public String getAttachmentPath() {
		return attachmentPath;
	}
	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
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

   
}