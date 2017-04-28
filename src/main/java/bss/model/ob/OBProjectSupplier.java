package bss.model.ob;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
* @ClassName: OBProjectSupplier 
* @Description: OBProjectSupplier  pojo
* @author Easong
* @date 2017年3月17日 下午12:46:56 
*
 */
public class OBProjectSupplier implements Serializable {
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	private String id;

    /**竞价管理ID**/
    private String projectId;

    /**竞价信息**/
    private List<OBProject> obProjectList;
    
    /**供应商 ID**/
    private String supplierId;

    /**创建时间**/
    private Date createdAt;

    /**修改时间**/
    private Date updatedAt;

    /**备注**/
    private String remark;
    
    
    /**目录末节点id**/
    private String supplierPrimaryId;

    public String getSupplierPrimaryId() {
		return supplierPrimaryId;
	}

	public void setSupplierPrimaryId(String supplierPrimaryId) {
		this.supplierPrimaryId = supplierPrimaryId;
	}

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

	public List<OBProject> getObProjectList() {
		return obProjectList;
	}

	public void setObProjectList(List<OBProject> obProjectList) {
		this.obProjectList = obProjectList;
	}
    
}