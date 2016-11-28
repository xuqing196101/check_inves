package ses.model.bms;


import java.util.Date;
import java.util.List;

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
     *  物资分类
     */
    private Integer classify;
    /**
     * @Fields code : 编码
     */
  
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

   private String kind;
   /**
    * @Fields paramStatus : 参数状态
    */
   private Integer paramStatus;
   /**
    * @Fields acceptRange : 验证规范
    */

   private String acceptRange;
   /**
    * @Fields paramPublishRange : 公布范围
    */

   private String paramPublishRange;

   private CategoryAttachment categoryAttchment;

   private List<CategoryAptitude> categoryAptitudes;
	
   private List<CategoryParam> categoryParams;
   
   private List<SupplierType> supplierTypes;
   
   private Orgnization orgnization;
   
   /** 审核日期 **/
   private Date auditDate;
   
   /** 审核人Id **/
   private String auditPersonId;
   
   /** 审核意见 */
   private String auditAdvise;
   
   
   
public Orgnization getOrgnization() {
	return orgnization;
}

public void setOrgnization(Orgnization orgnization) {
	this.orgnization = orgnization;
}

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

    

	public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
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

	public String getParamPublishRange() {
		return paramPublishRange;
	}

	public void setParamPublishRange(String paramPublishRange) {
		this.paramPublishRange = paramPublishRange;
	}

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditPersonId() {
        return auditPersonId;
    }

    public void setAuditPersonId(String auditPersonId) {
        this.auditPersonId = auditPersonId;
    }

    public String getAuditAdvise() {
        return auditAdvise;
    }

    public void setAuditAdvise(String auditAdvise) {
        this.auditAdvise = auditAdvise;
    }

	
	

	
    
}