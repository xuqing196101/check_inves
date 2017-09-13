package extract.model.expert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * Description: 抽取条件详细专家类别信息
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractTypeInfo {

    /**
     * 主键
     */
    private String id;

    /**
     * 人数
     */
    private BigDecimal countPerson;

    /**
     * 工程专业信息（工程专有）
     */
    private String engInfo;

    /**
     * 产品类别
     */
    private String categoryIds;

    /**
     * 技术职称
     */
    private String technicalTitle;

    /**
     * 工程执业资格（工程专有）
     */
    private String engQualification;

    /**
     * 抽取条件ID
     */
    private String conditionId;

    /**
     * 专家类别code
     */
    private String expertTypeCode;

    /**
     * 产品类别是否同时满足
     */
    private Short isSatisfy;
    
    /**
     * 工程专业信息是否同时满足
     */
    private Short engIsSatisfy;

    /**
     * 删除标识
     */
    private Short isDeleted;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public BigDecimal getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(BigDecimal countPerson) {
        this.countPerson = countPerson;
    }

    public String getEngInfo() {
        return engInfo;
    }

    public void setEngInfo(String engInfo) {
        this.engInfo = engInfo == null ? null : engInfo.trim();
    }

    public String getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(String categoryIds) {
        this.categoryIds = categoryIds == null ? null : categoryIds.trim();
    }

    public String getTechnicalTitle() {
        return technicalTitle;
    }

    public void setTechnicalTitle(String technicalTitle) {
        this.technicalTitle = technicalTitle == null ? null : technicalTitle.trim();
    }

    public String getEngQualification() {
        return engQualification;
    }

    public void setEngQualification(String engQualification) {
        this.engQualification = engQualification == null ? null : engQualification.trim();
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId == null ? null : conditionId.trim();
    }

    public String getExpertTypeCode() {
        return expertTypeCode;
    }

    public void setExpertTypeCode(String expertTypeCode) {
        this.expertTypeCode = expertTypeCode == null ? null : expertTypeCode.trim();
    }

    public Short getIsSatisfy() {
        return isSatisfy;
    }

    public void setIsSatisfy(Short isSatisfy) {
        this.isSatisfy = isSatisfy;
    }

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
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

    public Short getEngIsSatisfy() {
        return engIsSatisfy;
	}

    public void setEngIsSatisfy(Short engIsSatisfy) {
        this.engIsSatisfy = engIsSatisfy;
    }

}