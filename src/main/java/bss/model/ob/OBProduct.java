package bss.model.ob;

import java.util.Date;
import java.util.List;

import ses.model.bms.Category;

/**
 * 
 * Description： 定型产品实体
 * 
 * @author  zhang shubin
 * @version  
 * @since JDK1.7
 * @date 2017年3月7日 下午6:11:25 
 *
 */
public class OBProduct {
	
	private OBProduct obProduct;
	private List<OBSupplier> obSupplierList;
	private String id;

	private String code;

	private String name;

	private String procurementId;

	private String categoryParentId;

	private String categoryId;

	private String standardModel;

	private String qualityTechnicalStandard;

	private String remark;

	private Integer isDeleted;

	private String createrId;

	private Date createdAt;

	private Date updatedAt;

	private Integer status;

	private Category category;

	private Category categoryParent;
	
	public OBProduct getObProduct() {
		return obProduct;
	}

	public void setObProduct(OBProduct obProduct) {
		this.obProduct = obProduct;
	}

	public List<OBSupplier> getObSupplierList() {
		return obSupplierList;
	}

	public void setObSupplierList(List<OBSupplier> obSupplierList) {
		this.obSupplierList = obSupplierList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getProcurementId() {
		return procurementId;
	}

	public void setProcurementId(String procurementId) {
		this.procurementId = procurementId == null ? null : procurementId
				.trim();
	}

	public String getCategoryParentId() {
		return categoryParentId;
	}

	public void setCategoryParentId(String categoryParentId) {
		this.categoryParentId = categoryParentId == null ? null
				: categoryParentId.trim();
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId == null ? null : categoryId.trim();
	}

	public String getStandardModel() {
		return standardModel;
	}

	public void setStandardModel(String standardModel) {
		this.standardModel = standardModel == null ? null : standardModel
				.trim();
	}

	public String getQualityTechnicalStandard() {
		return qualityTechnicalStandard;
	}

	public void setQualityTechnicalStandard(String qualityTechnicalStandard) {
		this.qualityTechnicalStandard = qualityTechnicalStandard == null ? null
				: qualityTechnicalStandard.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId == null ? null : createrId.trim();
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Category getCategoryParent() {
		return categoryParent;
	}

	public void setCategoryParent(Category categoryParent) {
		this.categoryParent = categoryParent;
	}

}