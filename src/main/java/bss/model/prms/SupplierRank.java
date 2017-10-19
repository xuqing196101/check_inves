package bss.model.prms;

import java.math.BigDecimal;

/**
 * 版权：(C) 版权所有 2011-2016
 * <简述>
 * 供应商排名情况
 * <详细描述>
 * @author   WangHuijie
 * @version  1.0.0
 * @since 2016年12月21日21:05:26
 * @see
 */
public class SupplierRank {

    /* 项目Id */
    private String projectId;
    /* 包Id */
    private String packageId;
    /* 供应商Id */
    private String supplierId;
    /* 专家Id */
    private String expertId;
    /* 经济总分 */
    private BigDecimal econScore;
    /* 技术总分 */
    private BigDecimal techScore;
    /* 技术总分 */
    private BigDecimal sumScore;
    /* 名次 */
    private Integer rank;
    /** 评审结果 **/
    private String reviewResult;
    
    private BigDecimal priceScore;
    
    public String getProjectId() {
        return projectId;
    }
    
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public String getPackageId() {
        return packageId;
    }
    
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    
    public String getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getExpertId() {
        return expertId;
    }
    
    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }
    
    public BigDecimal getEconScore() {
        return econScore;
    }
    
    public void setEconScore(BigDecimal econScore) {
        this.econScore = econScore;
    }
    
    public BigDecimal getTechScore() {
        return techScore;
    }
    
    public void setTechScore(BigDecimal techScore) {
        this.techScore = techScore;
    }
    
    public Integer getRank() {
        return rank;
    }
    
    public void setRank(Integer rank) {
        this.rank = rank;
    }
    
    public BigDecimal getSumScore() {
        return sumScore;
    }

    public void setSumScore(BigDecimal sumScore) {
        this.sumScore = sumScore;
    }
    
    /**
     * 无参构造方法
     */
    public SupplierRank() {}

    public String getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(String reviewResult) {
        this.reviewResult = reviewResult;
    }

    public BigDecimal getPriceScore() {
      return priceScore;
    }

    public void setPriceScore(BigDecimal priceScore) {
      this.priceScore = priceScore;
    }
    
    
}
