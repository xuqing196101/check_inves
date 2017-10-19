package ses.model.bms;


import java.util.Date;
import java.util.List;

import ses.model.oms.Orgnization;
import ses.model.ppms.CategoryParam;
import ses.model.sms.SupplierType;


/**
 * @Title:Category
 * @Description:采购目录实体类
 * @author Zhang XueFeng
 * @date 2016-8-25-下午6:24:23
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
     * @Fields EXPERT_TYPE : 专家类型 0工程经济  1工程技术 
     */
    
    private String expertType;

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
     * @Fields udpatedAt : 修改时间
     */
    private Date updatedAt;

    /**
     * @Fields description : 描述
     */
    private String description;

    /**
     *  物资分类 null 其他，3物资生产/物资销售 2物质销售 1物资生产
     */
    private Integer classify;
    
    /**
     * 1：物资 ，2：工程 3：服务
     */
    private Integer type;

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

    /** 通用资质Ids **/
    private transient String generalQuaIds;

    /** 通用资质names **/
    private transient String generalQuaNames;

    /** 物资生产型专业资质ids **/
    private transient String profileQuaIds;

    /** 物资生产型专业资质names **/
    private transient String profileQuaNames;

    /** 物资销售型专业资质ids **/
    private transient String profileSalesQuaIds;

    /** 物资销售型专业资质names **/
    private transient String profileSalesQuaNames;

    /** 等级 **/
    private Integer level;
    
    private String engLevel;//等级
    private String isParent;//是否为子节点

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

    public String getGeneralQuaIds() {
        return generalQuaIds;
    }

    public void setGeneralQuaIds(String generalQuaIds) {
        this.generalQuaIds = generalQuaIds;
    }

    public String getGeneralQuaNames() {
        return generalQuaNames;
    }

    public void setGeneralQuaNames(String generalQuaNames) {
        this.generalQuaNames = generalQuaNames;
    }

    public String getProfileQuaIds() {
        return profileQuaIds;
    }

    public void setProfileQuaIds(String profileQuaIds) {
        this.profileQuaIds = profileQuaIds;
    }

    public String getProfileQuaNames() {
        return profileQuaNames;
    }

    public void setProfileQuaNames(String profileQuaNames) {
        this.profileQuaNames = profileQuaNames;
    }

    public String getProfileSalesQuaIds() {
        return profileSalesQuaIds;
    }

    public void setProfileSalesQuaIds(String profileSalesQuaIds) {
        this.profileSalesQuaIds = profileSalesQuaIds;
    }

    public String getProfileSalesQuaNames() {
        return profileSalesQuaNames;
    }

    public void setProfileSalesQuaNames(String profileSalesQuaNames) {
        this.profileSalesQuaNames = profileSalesQuaNames;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getEngLevel() {
        return engLevel;
    }

    public void setEngLevel(String engLevel) {
        this.engLevel = engLevel;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

	public String getIsParent() {
      return isParent;
    }

    public void setIsParent(String isParent) {
      this.isParent = isParent;
    }

  @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acceptRange == null) ? 0 : acceptRange.hashCode());
		result = prime * result
				+ ((auditAdvise == null) ? 0 : auditAdvise.hashCode());
		result = prime * result
				+ ((auditDate == null) ? 0 : auditDate.hashCode());
		result = prime * result
				+ ((auditPersonId == null) ? 0 : auditPersonId.hashCode());
		result = prime
				* result
				+ ((categoryAptitudes == null) ? 0 : categoryAptitudes
						.hashCode());
		result = prime
				* result
				+ ((categoryAttchment == null) ? 0 : categoryAttchment
						.hashCode());
		result = prime * result
				+ ((categoryParams == null) ? 0 : categoryParams.hashCode());
		result = prime * result
				+ ((classify == null) ? 0 : classify.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((engLevel == null) ? 0 : engLevel.hashCode());
		result = prime * result
				+ ((expertType == null) ? 0 : expertType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result
				+ ((isPublish == null) ? 0 : isPublish.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((level == null) ? 0 : level.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((orgnization == null) ? 0 : orgnization.hashCode());
		result = prime
				* result
				+ ((paramPublishRange == null) ? 0 : paramPublishRange
						.hashCode());
		result = prime * result
				+ ((paramStatus == null) ? 0 : paramStatus.hashCode());
		result = prime * result
				+ ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((supplierTypes == null) ? 0 : supplierTypes.hashCode());
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
		Category other = (Category) obj;
		if (acceptRange == null) {
			if (other.acceptRange != null)
				return false;
		} else if (!acceptRange.equals(other.acceptRange))
			return false;
		if (auditAdvise == null) {
			if (other.auditAdvise != null)
				return false;
		} else if (!auditAdvise.equals(other.auditAdvise))
			return false;
		if (auditDate == null) {
			if (other.auditDate != null)
				return false;
		} else if (!auditDate.equals(other.auditDate))
			return false;
		if (auditPersonId == null) {
			if (other.auditPersonId != null)
				return false;
		} else if (!auditPersonId.equals(other.auditPersonId))
			return false;
		if (categoryAptitudes == null) {
			if (other.categoryAptitudes != null)
				return false;
		} else if (!categoryAptitudes.equals(other.categoryAptitudes))
			return false;
		if (categoryAttchment == null) {
			if (other.categoryAttchment != null)
				return false;
		} else if (!categoryAttchment.equals(other.categoryAttchment))
			return false;
		if (categoryParams == null) {
			if (other.categoryParams != null)
				return false;
		} else if (!categoryParams.equals(other.categoryParams))
			return false;
		if (classify == null) {
			if (other.classify != null)
				return false;
		} else if (!classify.equals(other.classify))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (engLevel == null) {
			if (other.engLevel != null)
				return false;
		} else if (!engLevel.equals(other.engLevel))
			return false;
		if (expertType == null) {
			if (other.expertType != null)
				return false;
		} else if (!expertType.equals(other.expertType))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (isPublish == null) {
			if (other.isPublish != null)
				return false;
		} else if (!isPublish.equals(other.isPublish))
			return false;
		if (kind == null) {
			if (other.kind != null)
				return false;
		} else if (!kind.equals(other.kind))
			return false;
		if (level == null) {
			if (other.level != null)
				return false;
		} else if (!level.equals(other.level))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orgnization == null) {
			if (other.orgnization != null)
				return false;
		} else if (!orgnization.equals(other.orgnization))
			return false;
		if (paramPublishRange == null) {
			if (other.paramPublishRange != null)
				return false;
		} else if (!paramPublishRange.equals(other.paramPublishRange))
			return false;
		if (paramStatus == null) {
			if (other.paramStatus != null)
				return false;
		} else if (!paramStatus.equals(other.paramStatus))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (supplierTypes == null) {
			if (other.supplierTypes != null)
				return false;
		} else if (!supplierTypes.equals(other.supplierTypes))
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}