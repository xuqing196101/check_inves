package bss.model.ppms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 版权：(C) 版权所有 
 * <简述>投标指标值以及得分实体
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public class AduitQuota implements Serializable{
    
    private static final long serialVersionUID = 1L;

    /**
     * @Fields id : 主键
     */
    private String id;

    /**
     * @Fields projectId : 项目id
     */
    private String projectId;

    /**
     * @Fields packageId : 包id
     */
    private String packageId;

    /**
     * @Fields supplierId : 供应商id
     */
    private String supplierId;

    /**
     * @Fields scoreModelId : 评分细则与模型关联id
     */
    private String scoreModelId;
    
    /**
     * @Fields supplierValue : 供应商绑定值
     */
    private BigDecimal supplierValue;

    /**
     * @Fields initScore : 初始得分
     */
    private BigDecimal initScore;

    /**
     * @Fields expertValue : 专家评审值
     */
    private BigDecimal expertValue;

    /**
     * @Fields finalScore : 最终得分
     */
    private BigDecimal finalScore;

    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields updatedAt : 更新时间
     */
    private Date updatedAt;

    /**
     * @Fields isDeleted : 删除标志 0：未删除 1：删除
     */
    private Short isDeleted;

    /**
     * @Fields round : 轮次
     */
    private Integer round;
    
    /**
     * @Fields page : 页码
     */
    private Integer page;

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

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public String getScoreModelId() {
        return scoreModelId;
    }

    public void setScoreModelId(String scoreModelId) {
        this.scoreModelId = scoreModelId == null ? null : scoreModelId.trim();
    }

    public BigDecimal getSupplierValue() {
        return supplierValue;
    }

    public void setSupplierValue(BigDecimal supplierValue) {
        this.supplierValue = supplierValue;
    }

    public BigDecimal getInitScore() {
        return initScore;
    }

    public void setInitScore(BigDecimal initScore) {
        this.initScore = initScore;
    }

    public BigDecimal getExpertValue() {
        return expertValue;
    }

    public void setExpertValue(BigDecimal expertValue) {
        this.expertValue = expertValue;
    }

    public BigDecimal getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(BigDecimal finalScore) {
        this.finalScore = finalScore;
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

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    
}