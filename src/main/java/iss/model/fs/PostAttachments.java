package iss.model.fs;

import java.io.Serializable;
import java.util.Date;

/**
* @Title:PostAttachments 
* @Description: 帖子附件实体类
* @author Peng Zhongjun
* @date 2016-10-14下午3:54:46
 */
public class PostAttachments implements Serializable{
	
	/**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields name : 文件名称
     */
    private String name;
    
    /**
     * @Fields type : 文件类型
     */
    private String type;
    
    /**
     * @Fields fileSize : 文件大小
     */
    private Float fileSize;
    
    /**
     * @Fields path : 文件路径
     */
    private String path;
    
    /**
     * @Fields createdAt : 文件创建时间
     */
    private Date createdAt;
    
    /**
     * @Fields updatedAt : 文件修改时间
     */
    private Date updatedAt;
    
    /**
     * @Fields post : 文件关联的帖子
     */
    private Post post;
    
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
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public Float getFileSize() {
		return fileSize;
	}

	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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