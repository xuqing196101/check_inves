package ses.model.sms;

import java.io.Serializable;
import java.util.Date;

public class SupplierItemLevel implements Serializable{
	/**
	 * SupplierItemLevel.java
	 */
	private static final long serialVersionUID = 1L;
	//主键
    private String id;
    //供应商id
    private String supplierId;
    //供应商等级
    private String supplierLevel;
    //工程等级名称
    private String supplierLevelName;
    //目录节点
    private String categoryId;
    //计算时间
    private Date createdAt;
    //目录类别
    private String supplierTypeId;
    //供应商名称
    private String supplierName;
    //联系人
    private String armyBusinessName;
    //联系人电话 armyBuinessTelephone
    private String armyBuinessTelephone;
    //采购机构 orgName
    private String orgName;
    
    public String getArmyBuinessTelephone() {
		return armyBuinessTelephone;
	}

	public void setArmyBuinessTelephone(String armyBuinessTelephone) {
		this.armyBuinessTelephone = armyBuinessTelephone;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getArmyBusinessName() {
		return armyBusinessName;
	}

	public void setArmyBusinessName(String armyBusinessName) {
		this.armyBusinessName = armyBusinessName;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getSupplierLevel() {
        return supplierLevel;
    }

    public void setSupplierLevel(String supplierLevel) {
        this.supplierLevel = supplierLevel == null ? null : supplierLevel.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getSupplierTypeId() {
        return supplierTypeId;
    }

    public void setSupplierTypeId(String supplierTypeId) {
        this.supplierTypeId = supplierTypeId == null ? null : supplierTypeId.trim();
    }

	public String getSupplierLevelName() {
		return supplierLevelName;
	}

	public void setSupplierLevelName(String supplierLevelName) {
		this.supplierLevelName = supplierLevelName;
	}
    
}