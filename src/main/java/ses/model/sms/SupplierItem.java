package ses.model.sms;

import common.model.UploadFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierItem implements Serializable {
	private static final long serialVersionUID = -6757464247201483546L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;
	
	/**
	 * <pre>
	 * 供应商ID
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.QUALIFICATION_TYPE
	 * </pre>
	 */
	private String qualificationType;

	/**
	 * <pre>
	 * 品目ID
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.CATEGORY_ID
	 * </pre>
	 */
	private String categoryId;

	/**
	 * <pre>
	 * 供应商类型
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.SUPPLIER_TYPE_RELATE_ID
	 * </pre>
	 */
	private String supplierTypeRelateId;

	/**
	 * <pre>
	 * 状态
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.STATUS
	 * </pre>
	 */
	private Integer status;

	/**
	 * <pre>
	 * 创建时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;
	
	/**
	 * <pre>
	 * 品目是否被退回（0：未被退回；1：已被退回）
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.IS_RETURNED
	 * </pre>
	 */
	private byte isReturned;
	
	/**
	 * <pre>
	 * 是否删除（0：未删除；1：已删除）
	 * 表字段 : T_SES_SMS_SUPPLIER_ITEMS.IS_DELETED
	 * </pre>
	 */
	private byte isDeleted;

	private String categoryName;

	private Integer nodeLevel;

	private List<SupplierProducts> listSupplierProducts = new ArrayList<SupplierProducts>();

	private String addProCategoryIds;

	private String deleteProCategoryIds;

	private String addSellCategoryIds;

	private String deleteSellCategoryIds;

	private String addEngCategoryIds;

	private String deleteEngCategoryIds;

	private String addServeCategoryIds;

	private String deleteServeCategoryIds;
	
	/**等级**/
	private String level;
	
	/**自定义等级**/
	private String diyLevel;
	
	/**证书编号**/
	private String certCode;

	private List<UploadFile> fileList=new ArrayList<UploadFile>();
	
	private String professType;
	
	public String getProfessType() {
		return professType;
	}

	public void setProfessType(String professType) {
		this.professType = professType;
	}

	public List<UploadFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<UploadFile> fileList) {
		this.fileList = fileList;
	}

	public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSupplierTypeRelateId() {
		return supplierTypeRelateId;
	}

	public void setSupplierTypeRelateId(String supplierTypeRelateId) {
		this.supplierTypeRelateId = supplierTypeRelateId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<SupplierProducts> getListSupplierProducts() {
		return listSupplierProducts;
	}

	public void setListSupplierProducts(List<SupplierProducts> listSupplierProducts) {
		this.listSupplierProducts = listSupplierProducts;
	}

	public String getAddProCategoryIds() {
		return addProCategoryIds;
	}

	public void setAddProCategoryIds(String addProCategoryIds) {
		this.addProCategoryIds = addProCategoryIds;
	}

	public String getDeleteProCategoryIds() {
		return deleteProCategoryIds;
	}

	public void setDeleteProCategoryIds(String deleteProCategoryIds) {
		this.deleteProCategoryIds = deleteProCategoryIds;
	}

	public String getAddSellCategoryIds() {
		return addSellCategoryIds;
	}

	public void setAddSellCategoryIds(String addSellCategoryIds) {
		this.addSellCategoryIds = addSellCategoryIds;
	}

	public String getDeleteSellCategoryIds() {
		return deleteSellCategoryIds;
	}

	public void setDeleteSellCategoryIds(String deleteSellCategoryIds) {
		this.deleteSellCategoryIds = deleteSellCategoryIds;
	}

	public String getAddEngCategoryIds() {
		return addEngCategoryIds;
	}

	public void setAddEngCategoryIds(String addEngCategoryIds) {
		this.addEngCategoryIds = addEngCategoryIds;
	}

	public String getDeleteEngCategoryIds() {
		return deleteEngCategoryIds;
	}

	public void setDeleteEngCategoryIds(String deleteEngCategoryIds) {
		this.deleteEngCategoryIds = deleteEngCategoryIds;
	}

	public String getAddServeCategoryIds() {
		return addServeCategoryIds;
	}

	public void setAddServeCategoryIds(String addServeCategoryIds) {
		this.addServeCategoryIds = addServeCategoryIds;
	}

	public String getDeleteServeCategoryIds() {
		return deleteServeCategoryIds;
	}

	public void setDeleteServeCategoryIds(String deleteServeCategoryIds) {
		this.deleteServeCategoryIds = deleteServeCategoryIds;
	}

    public String getDiyLevel() {
        return diyLevel;
    }

    public void setDiyLevel(String diyLevel) {
        this.diyLevel = diyLevel;
    }

    public String getQualificationType() {
        return qualificationType;
    }

    public void setQualificationType(String qualificationType) {
        this.qualificationType = qualificationType;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

	public byte getIsReturned() {
		return isReturned;
	}

	public void setIsReturned(byte isReturned) {
		this.isReturned = isReturned;
	}

	public byte getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}
    
}