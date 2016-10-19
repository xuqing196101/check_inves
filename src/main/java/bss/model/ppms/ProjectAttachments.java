package bss.model.ppms;



import java.io.Serializable;
import java.util.Date;

public class ProjectAttachments implements Serializable{
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
     * @Fields task : 文件关联的任务
     */
    private Project project;
    
    /**
     * @Fields isDeleted : 文件是否删除
     */
    private Integer isDeleted;
    
    /**
     * 0：立项；1：招标文件；2：招标公告；3：招标公告审批文件；
     */
    private Integer kind;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }
    
    
}
