package ses.model.bms;

import java.io.Serializable;
import java.util.Date;

/*
 *@Title:ArticleAttachments
 *@Description:信息附件实体类
 *@author QuJie
 *@date 2016-9-7下午6:19:08
 */
public class ArticleAttachments implements Serializable{
	
	/**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields fileName : 文件名称
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
     * @Fields createdAt : 文件创建时间
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt : 文件修改时间
     */
    private Date updatedAt;
    
    /**
     * @Fields article : 文件关联的文章
     */
    private Article article;
    
    /**
     * @Fields isDeleted : 文件是否删除
     */
    private Integer isDeleted;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType == null ? null : contentType.trim();
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
        this.attachmentPath = attachmentPath == null ? null : attachmentPath.trim();
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