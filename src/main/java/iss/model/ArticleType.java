package iss.model;

import java.io.Serializable;
import java.util.Date;

/*
 *@Title:ArticleType
 *@Description:文章类型实体类
 *@author QuJie
 *@date 2016-9-7下午6:24:23
 */
public class ArticleType implements Serializable{
	
	/**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields name : 栏目名称
     */
    private String name;
    
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
        this.name = name == null ? null : name.trim();
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