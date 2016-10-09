package iss.model.ps;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.poi.util.NotImplemented;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import ses.model.bms.User;

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
    @NotBlank(message = "栏目名称不能为空")
    private String name;
    /**
     * @Fields describe : 栏目描述
     */
    private String describe;
    /**
     * @Fields creater: 创建人
     */
    private User creater;   
    /**
     * @Fields parent:上级栏目
     */
    private ArticleType parent;
    /**
     * @Fields createddAt :创建时间
     */
    private Timestamp createdAt;
    /**
     * @Fields updatedAt : 修改时间
     */
    private Timestamp updatedAt;
    /**
     * @Fields parent:子栏目
     */
    private List<ArticleType> children;

      
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
        this.name = name == null ? null : name.trim();
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }


	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public ArticleType getParent() {
		return parent;
	}

	public void setParent(ArticleType parent) {
		this.parent = parent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((children == null) ? 0 : children.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((creater == null) ? 0 : creater.hashCode());
		result = prime * result
				+ ((describe == null) ? 0 : describe.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result
				+ ((updatedAt == null) ? 0 : updatedAt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticleType other = (ArticleType) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
/*		if (creater == null) {
			if (other.creater != null)
				return false;
		} else if (!creater.equals(other.creater))
			return false;*/
		if (describe == null) {
			if (other.describe != null)
				return false;
		} else if (!describe.equals(other.describe))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}


	

}