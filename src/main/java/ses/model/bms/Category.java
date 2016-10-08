package ses.model.bms;


import java.util.Date;
import java.util.List;

import ses.model.ppms.CategoryParam;
import ses.model.sms.SupplierType;

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
    /*
     * 是否公开
     * 
     * */
   private Integer isPublish;
   /*
    * 产品类型（销售型   生产型）
    * 
    * */
   private Integer kind;
   /*
    * 参数状态
    * 
    * */
   private Integer paramStatus;
   /*
    * 验证规范
    * 
    * */
   private String acceptRange;
   /*
    * 公开范围 （内网  外网）
    * 
    * 
    * */
   private Integer paramPublishRange;

   private CategoryAttchment categoryAttchment;
   
   private List<CategoryAptitude> categoryAptitudes;
	
   private List<CategoryParam> categoryParams;
   
   private List<SupplierType> supplierTypes;
   


	public List<SupplierType> getSupplierTypes() {
	return supplierTypes;
}

public void setSupplierTypes(List<SupplierType> supplierTypes) {
	this.supplierTypes = supplierTypes;
}

	public List<CategoryAptitude> getCategoryAptitudes() {
	return categoryAptitudes;
}

public void setCategoryAptitudes(List<CategoryAptitude> categoryAptitudes) {
	this.categoryAptitudes = categoryAptitudes;
}

	public CategoryAttchment getCategoryAttchment() {
	return categoryAttchment;
}

public void setCategoryAttchment(CategoryAttchment categoryAttchment) {
	this.categoryAttchment = categoryAttchment;
}

    
    public List<CategoryParam> getCategoryParams() {
		return categoryParams;
	}

	public void setCategoryParams(List<CategoryParam> categoryParams) {
		this.categoryParams = categoryParams;
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



	public Integer getParamStatus() {
		return paramStatus;
	}

	public void setParamStatus(Integer paramStatus) {
		this.paramStatus = paramStatus;
	}

	public Integer getIsPublish() {
		return isPublish;
	}

	public void setIsPublish(Integer isPublish) {
		this.isPublish = isPublish;
	}

	public Integer getKind() {
		return kind;
	}

	public void setKind(Integer kind) {
		this.kind = kind;
	}

	public String getAcceptRange() {
		return acceptRange;
	}

	public void setAcceptRange(String acceptRange) {
		this.acceptRange = acceptRange;
	}

	public Integer getParamPublishRange() {
		return paramPublishRange;
	}

	public void setParamPublishRange(Integer paramPublishRange) {
		this.paramPublishRange = paramPublishRange;
	}

	

	
    
}