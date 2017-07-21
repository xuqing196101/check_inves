package ses.model.bms;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>资质实体
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class Qualification {
    
    /** 主键 **/
    private String id;
    /** 名称 **/
    private String name;
    /** 类型,1:通用,2:专业,4:工程**/ 
    private Integer type;
    /** 是否删除,0:未删除,1:删除 **/
    private Integer isDeleted;
    /** 创建日期 **/
    private Date createdAt;
    /** 修改日期**/
    private Date updatedAt;
    /** 唯一标示flag(供应商资质文件维护) **/
    private String flag;
    //临时存储 supplierItemId
    private String supplierItemId;
    //临时存储 审核数量
    private Integer auditCount;
    public Integer getAuditCount() {
		return auditCount;
	}

	public void setAuditCount(Integer auditCount) {
		this.auditCount = auditCount;
	}

	public String getSupplierItemId() {
		return supplierItemId;
	}

	public void setSupplierItemId(String supplierItemId) {
		this.supplierItemId = supplierItemId;
	}

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
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    
}
