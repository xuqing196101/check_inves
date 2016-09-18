package ses.model.bms;


import java.util.Date;
import java.util.List;

public class Category {
	/*
	 * 目录id
	 * 
	 * */
    private String id;
    /*
     * 目录名称
     * 
     * */
    private String name;
    /*
     * 状态（激活、休眠）
     * 
     * */
    private Integer status;
    /*
     * 层级（pid）
     * 
     * 
     * */
    private String parentId;
    /*
     * 创建时间
     * 
     * */
    private Date createdAt;
    /*
     * 更新时间
     * 
     * */
    private Date updatedAt;


    /*
     * 描述
     * 
     * */
    private String description;
    /*
     * 是否为末级
     * 
     * */
    private String isEnd;
    /*
     * 前台展示优先级
     * 
     * */
    private String code;
    /*
     * 排序号
     * 
     * */
    private Integer position;
    /*
     * 是否删除
     * 
     * */
    private Integer isDeleted;

	private List<CategoryAttchment> categoryAttchments;
    
    public List<CategoryAttchment> getCategoryAttchments() {
		return categoryAttchments;
	}

	public void setCategoryAttchments(List<CategoryAttchment> categoryAttchments) {
		this.categoryAttchments = categoryAttchments;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

   

    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId == null ? null : parentId.trim();
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

  

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(String isEnd) {
        this.isEnd = isEnd == null ? null : isEnd.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

	public Category(String id) {
	
		this.id = id;
	}

	public Category() {
		super();
	}

	public void setCode(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		
	}
    
    
}