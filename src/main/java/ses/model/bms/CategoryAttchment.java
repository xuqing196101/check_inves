package ses.model.bms;

import java.util.Date;

/*
 *@Title:CategoryAttchment
 *@Description:采购目录附件实体类
 *@author Zhang XueFeng
 *@date 
 */
public class CategoryAttchment {
	/**
	 * @Fields id
	 * 
	 * */
    private String id;
    /**
	 * @Fields 附件名称
	 * 
	 * */
    private String fileName;
    /**
	 * @Fields 文件类型
	 * 
	 * */
    private String contentType;
    /**
	 * @Fields 文件大小
	 * 
	 * */
    private Float fileSize;
    /**
	 * @Fields 附件路径
	 * 
	 * */
    private String attchmentPath;
    /**
	 * @Fields 创建时间
	 * 
	 * */
    private Date createdAt;
    /**
	 * @Fields 更新时间
	 * 
	 * */
    private Date updatedAt;
    /**
	 * @Fields id
	 * 
	 * */
    private Category category;
    /**
	 * @Fields 是否删除
	 * 
	 * */
    private Short isDeleted;
    
    

    public CategoryAttchment() {
		super();
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getAttchmentPath() {
        return attchmentPath;
    }

    public void setAttchmentPath(String attchmentPath) {
        this.attchmentPath = attchmentPath == null ? null : attchmentPath.trim();
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

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }
}