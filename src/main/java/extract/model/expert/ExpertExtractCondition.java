package extract.model.expert;

import java.util.Date;

/**
 * 
 * Description: 专家抽取条件实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractCondition {
    
    /**
     * 主键
     */
    private String id;

    /**
     * 区域要求   0代表全国
     */
    private String areaName;

    /**
     * 专家类型 0是不限
     */
    private String expertTypeId;

    /**
     * 专家类别
     */
    private String expertKindId;

    /**
     * 抽取总人数
     */
    private String extractNum;

    /**
     * 是否抽取候补专家 0 否   1 是
     */
    private Short isExtractAlternate;

    /**
     * 创建时间
     */
    private Date createdAt;

    /**
     * 修改时间
     */
    private Date updatedAt;

    /**
     * 删除标识
     */
    private Short isDeleted;

    /**
     * 限制地区理由
     */
    private String addressReason;

    /**
     * 关联抽取项目信息表id
     */
    private String projectId;
    
    /**
     * 地区ID
     */
    private String addressId;
     
    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getExpertTypeId() {
        return expertTypeId;
    }

    public void setExpertTypeId(String expertTypeId) {
        this.expertTypeId = expertTypeId == null ? null : expertTypeId.trim();
    }

    public String getExpertKindId() {
        return expertKindId;
    }

    public void setExpertKindId(String expertKindId) {
        this.expertKindId = expertKindId == null ? null : expertKindId.trim();
    }

    public String getExtractNum() {
        return extractNum;
    }

    public void setExtractNum(String extractNum) {
        this.extractNum = extractNum == null ? null : extractNum.trim();
    }

    public Short getIsExtractAlternate() {
        return isExtractAlternate;
    }

    public void setIsExtractAlternate(Short isExtractAlternate) {
        this.isExtractAlternate = isExtractAlternate;
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

    public Short getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Short isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getAddressReason() {
        return addressReason;
    }

    public void setAddressReason(String addressReason) {
        this.addressReason = addressReason == null ? null : addressReason.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }
}
