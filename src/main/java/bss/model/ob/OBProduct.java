package bss.model.ob;

import java.util.Date;

public class OBProduct {
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
        this.procurementId = procurementId == null ? null : procurementId.trim();
    }

    public String getCategoryParentId() {
        return categoryParentId;
    }

    public void setCategoryParentId(String categoryParentId) {
        this.categoryParentId = categoryParentId == null ? null : categoryParentId.trim();
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
        this.standardModel = standardModel == null ? null : standardModel.trim();
    }

    public String getQualityTechnicalStandard() {
        return qualityTechnicalStandard;
    }

    public void setQualityTechnicalStandard(String qualityTechnicalStandard) {
        this.qualityTechnicalStandard = qualityTechnicalStandard == null ? null : qualityTechnicalStandard.trim();
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
}