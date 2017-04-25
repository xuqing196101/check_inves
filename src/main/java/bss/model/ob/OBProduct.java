package bss.model.ob;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ses.model.bms.Category;

/**
 * 
 * Description： 定型产品实体
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月7日 下午6:11:25
 * 
 */
public class OBProduct implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private OBProduct obProduct;
	private List<OBSupplier> obSupplierList;
	private String id;

	private String code;

	private String name;

	private String procurementId;

	// 产品小类
	private String categoryId;

	// 产品中类
	private String categoryMiddleId;

	// 产品大类
	private String categoryBigId;

	// 产品类别
	private String productCategoryId;

	// 产品类别等级
	private Integer productCategoryLevel;

	// 产品规格型号
	private String standardModel;

	// 质量技术标准
	private String qualityTechnicalStandard;

	private String remark;

	private Integer isDeleted;

	private String createrId;

	private Date createdAt;

	private Date updatedAt;

	private Integer status;
	
	private String smallPointsId;

	public String getSmallPointsId() {
		return smallPointsId;
	}

	public void setSmallPointsId(String smallPointsId) {
			this.smallPointsId = smallPointsId;
	}

	// 产品小类
	private Category category;

	// 产品大类
	private Category categoryBig;

	// 产品中类
	private Category categoryMiddle;

	// 产品类别
	private Category productCategory;
	
	/**
	 * 目录全路径
	 */
	private String pointsName;
	
	private Category smallPoints;//末节点对应的实体对象

	public Category getSmallPoints() {
		return smallPoints;
	}

	public void setSmallPoints(Category smallPoints) {
		this.smallPoints = smallPoints;
	}

	public String getPointsName() {
		return pointsName;
	}

	public void setPointsName(String pointsName) {
		this.pointsName = pointsName;
	}

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

	public String getCategoryMiddleId() {
		return categoryMiddleId;
	}

	public void setCategoryMiddleId(String categoryMiddleId) {
		this.categoryMiddleId = categoryMiddleId;
	}

	public String getCategoryBigId() {
		return categoryBigId;
	}

	public void setCategoryBigId(String categoryBigId) {
		this.categoryBigId = categoryBigId;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public Integer getProductCategoryLevel() {
		return productCategoryLevel;
	}

	public void setProductCategoryLevel(Integer productCategoryLevel) {
		this.productCategoryLevel = productCategoryLevel;
	}

	public Category getCategoryBig() {
		return categoryBig;
	}

	public void setCategoryBig(Category categoryBig) {
		this.categoryBig = categoryBig;
	}

	public Category getCategoryMiddle() {
		return categoryMiddle;
	}

	public void setCategoryMiddle(Category categoryMiddle) {
		this.categoryMiddle = categoryMiddle;
	}

	public Category getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Category productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public String toString() {
		return "OBProduct [obProduct=" + obProduct + ", obSupplierList="
				+ obSupplierList + ", id=" + id + ", code=" + code + ", name="
				+ name + ", procurementId=" + procurementId + ", categoryId="
				+ categoryId + ", categoryMiddleId=" + categoryMiddleId
				+ ", categoryBigId=" + categoryBigId + ", productCategoryId="
				+ productCategoryId + ", productCategoryLevel="
				+ productCategoryLevel + ", standardModel=" + standardModel
				+ ", qualityTechnicalStandard=" + qualityTechnicalStandard
				+ ", remark=" + remark + ", isDeleted=" + isDeleted
				+ ", createrId=" + createrId + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", status=" + status
				+ ", category=" + category + ", categoryBig=" + categoryBig
				+ ", categoryMiddle=" + categoryMiddle + ", productCategory="
				+ productCategory + "]";
	}

}