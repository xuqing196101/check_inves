package ses.model.sms;

import java.util.Date;

public class SupplierItemRecy {
    private String id;

    private String supplierId;

    private String categoryId;

    private String supplierTypeRelateId;

    private Integer status;

    private Date createdAt;

    private Date updatedAt;

    private String cateLevel;

    private String certCode;

    private String diyLevel;

    private String qualificationType;

    private String professType;

    private Integer nodeLevel;

    private Integer isReturned;

    private Integer isDeleted;

    private Date recyTime;

    private String recyAptId;

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

    public String getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(String cateLevel) {
        this.cateLevel = cateLevel;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
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

    public String getProfessType() {
        return professType;
    }

    public void setProfessType(String professType) {
        this.professType = professType;
    }

    public Integer getNodeLevel() {
        return nodeLevel;
    }

    public void setNodeLevel(Integer nodeLevel) {
        this.nodeLevel = nodeLevel;
    }

    public Integer getIsReturned() {
        return isReturned;
    }

    public void setIsReturned(Integer isReturned) {
        this.isReturned = isReturned;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getRecyTime() {
        return recyTime;
    }

    public void setRecyTime(Date recyTime) {
        this.recyTime = recyTime;
    }

    public String getRecyAptId() {
        return recyAptId;
    }

    public void setRecyAptId(String recyAptId) {
        this.recyAptId = recyAptId;
    }
}