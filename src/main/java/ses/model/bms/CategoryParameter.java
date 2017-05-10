package ses.model.bms;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CategoryParameter implements Serializable{
    
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	/** 主键 */
    private String id;
    /** 品目类型ID */
    private String cateId;
    /** 参数名称 */
    private String paramName;
    /** 参数类型ID */
    private String paramTypeId;
    /** 创建日期 */
    private Date createdAt;
    /** 修改日期 */
    private Date updatedAt;
    /** 采购机构ID */
    private String orgId;
    /** 是否删除,1:删除,0:未删除 */
    private Integer isDeleted;
    /**
     * 是否必填，1填写，0可以不填
     */
    private Integer paramRequired;
    
    /** 参数类型名称 */
    private transient String paramTypeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamTypeId() {
        return paramTypeId;
    }

    public void setParamTypeId(String paramTypeId) {
        this.paramTypeId = paramTypeId;
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

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getParamTypeName() {
        return paramTypeName;
    }

    public void setParamTypeName(String paramTypeName) {
        this.paramTypeName = paramTypeName;
    }

	public Integer getParamRequired() {
		return paramRequired;
	}

	public void setParamRequired(Integer paramRequired) {
		this.paramRequired = paramRequired;
	}
    
    
    
}
