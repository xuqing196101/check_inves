package iss.model.ps;

import java.io.Serializable;
import java.util.Date;

import ses.model.bms.User;

/*
 *@Title:DownloadUser
 *@Description:下载人实体类
 *@author QuJie
 *@date 2016-9-7下午6:22:27
 */
public class DownloadUser implements Serializable{
	
	/**
     * @Fields id : 主键
     */
    private String id;
    
    /**
     * @Fields articleId : 文章id
     */
    private Article article;
    
    /**
     * @Fields userId : 用户id
     */
    private User user;
    
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

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
}