package bss.model.ob;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import ses.model.bms.Category;
import ses.model.sms.Supplier;

/**
 * 
 * Description： 定型产品竞价 供应商实体
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月7日 下午6:10:44
 * 
 */
public class OBSupplier implements Serializable {
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	private String id;

	private String supplierId;

	private String qualificationCert;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date certValidPeriod;

	private String qualityInspectionDep;

	private String contactName;

	private String contactTel;

	private String certCode;

	private String uscc;

	private String createrId;

	private Integer isDeleted;

	private String productId;

	private String remark;

	private Date createdAt;

	private Date updatedAt;

	private Supplier supplier;

	private Integer nCount;// 计数
	
	private String smallPointsId;//末节点id
	
	private Category smallPoints;//末节点对应的实体对象
	
	/**
	 * 目录全路径
	 */
	private String pointsName;
	
	public String getPointsName() {
		return pointsName;
	}

	public void setPointsName(String pointsName) {
		this.pointsName = pointsName;
	}

	public String getSmallPointsId() {
		return smallPointsId;
	}

	public void setSmallPointsId(String smallPointsId) {
		this.smallPointsId = smallPointsId;
	}

	public Category getSmallPoints() {
		return smallPoints;
	}

	public void setSmallPoints(Category smallPoints) {
		this.smallPoints = smallPoints;
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

	public String getQualificationCert() {
		return qualificationCert;
	}

	public void setQualificationCert(String qualificationCert) {
		this.qualificationCert = qualificationCert == null ? null
				: qualificationCert.trim();
	}

	public Date getCertValidPeriod() {
		return certValidPeriod;
	}

	public void setCertValidPeriod(Date certValidPeriod) {
		this.certValidPeriod = certValidPeriod;
	}

	public String getQualityInspectionDep() {
		return qualityInspectionDep;
	}

	public void setQualityInspectionDep(String qualityInspectionDep) {
		this.qualityInspectionDep = qualityInspectionDep == null ? null
				: qualityInspectionDep.trim();
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName == null ? null : contactName.trim();
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel == null ? null : contactTel.trim();
	}

	public String getCertCode() {
		return certCode;
	}

	public void setCertCode(String certCode) {
		this.certCode = certCode == null ? null : certCode.trim();
	}

	public String getUscc() {
		return uscc;
	}

	public void setUscc(String uscc) {
		this.uscc = uscc == null ? null : uscc.trim();
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId == null ? null : createrId.trim();
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId == null ? null : productId.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Integer getnCount() {
		return nCount;
	}

	public void setnCount(Integer nCount) {
		this.nCount = nCount;
	}

	@Override
	public String toString() {
		return "OBSupplier [id=" + id + ", supplierId=" + supplierId
				+ ", qualificationCert=" + qualificationCert
				+ ", certValidPeriod=" + certValidPeriod
				+ ", qualityInspectionDep=" + qualityInspectionDep
				+ ", contactName=" + contactName + ", contactTel=" + contactTel
				+ ", certCode=" + certCode + ", uscc=" + uscc + ", createrId="
				+ createrId + ", isDeleted=" + isDeleted + ", productId="
				+ productId + ", remark=" + remark + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", supplier=" + supplier
				+ ", nCount=" + nCount + "]";
	}

}