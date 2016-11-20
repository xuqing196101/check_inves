package ses.model.bms;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 
 *  产品分配表
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class CategoryAssigned {
    
    /** ID */
    private String id;
    /** 需求部门ID */
    private String orgId;
    /** 产品ID */
    private String cateId;
    /** 创建日期 */
    private Date createdAt;
    /** 修改日期 */
    private Date updatedAt;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
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
