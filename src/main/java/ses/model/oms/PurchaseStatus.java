package ses.model.oms;

import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 采购机构资质状态表
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class PurchaseStatus {
    
    /** 主键 **/
    private String id;
    /** 采购机构Id**/
    private String purchaseId;
    /** 状态 **/
    private Integer status;
    /** 原因 **/
    private String reason;
    /** 创建日期 **/
    private Date createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    
    
}
