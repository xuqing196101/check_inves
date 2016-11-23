package ses.model.bms;

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
public class CategoryParameter {
    
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
    /** 状态,0:未审核,1:审核通过,2:已发布 */
    private Integer status;
    /** 分配ID */
    private String orgCateId;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOrgCateId() {
        return orgCateId;
    }

    public void setOrgCateId(String orgCateId) {
        this.orgCateId = orgCateId;
    }
    
    
}
