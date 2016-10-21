package ses.model.bms;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

import ses.model.oms.Orgnization;
import ses.model.ppms.CategoryParam;
import ses.model.sms.SupplierType;
/**
 *@Title:Category
 *@Description:采购目录实体类
 *@author Zhang XueFeng
 *@date 2016-8-25-下午6:24:23
 */
public class Category {
	/**
     * @Fields id : 主键
     */
    private String id;
    /**
     * @Fields name :目录名称 
     */
    @NotBlank(message = "目录名称不能为空")
    private String name;
    /**
     * @Fields status : 状态（激活/休眠）
     */
    private Integer status;
    /**
     * @Fields parentId : 父节点
     */
    private String parentId;
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;
    /**
     * @Fields udpatedAt :  修改时间
     */
    private Date updatedAt;
    /**
     * @Fields description : 描述
     */
    private String description;
    /**
     * @Fields isEnd : 是否为末级
     */
    private Integer isEnd;
    /**
     * @Fields code : 编码
     */
    @NotBlank(message ="编码不能为空")
    private String code;
    /**
     * @Fields position : 排序号
     */
   
    private Integer position;
    /**
     * @Fields isDeleted : 是否删除
     */
    private Integer isDeleted;
    /**
     * @Fields isPublish : 是否公开
     */
   
   private Integer isPublish;
   /**
    * @Fields kind : 产品类型
    */
    @NotBlank(message = "请选择产品类型")
   private String kind;
   /**
    * @Fields paramStatus : 参数状态
    */
   private Integer paramStatus;
   /**
    * @Fields acceptRange : 验证规范
    */
   @NotBlank(message = "请输入验证规范")
   private String acceptRange;
   /**
    * @Fields paramPublishRange : 公布范围
    */

   private Integer paramPublishRange;

   private CategoryAttachment categoryAttchment;

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

	public CategoryAttachment getCategoryAttchment() {
	return categoryAttchment;
}

    public void setCategoryAttchment(CategoryAttachment categoryAttchment) {
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

    public Integer getIsEnd() {
		return isEnd;
	}

	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
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

	public String getAcceptRange() {
		return acceptRange;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
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