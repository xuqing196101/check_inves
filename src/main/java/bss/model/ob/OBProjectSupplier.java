package bss.model.ob;

import java.util.Date;
/**
 * 
* @ClassName: OBProjectSupplier 
* @Description: OBProjectSupplier  pojo
* @author Easong
* @date 2017年3月17日 下午12:46:56 
*
 */
public class OBProjectSupplier {
    private String id;

    /**竞价管理ID**/
    private String projectId;

    /**供应商 ID**/
    private String supplierId;

    /**创建时间**/
    private Date createdAt;

    /**修改时间**/
    private Date updatedAt;

    /**备注**/
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}