package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 版权：(C) 版权所有 
 * <谈判记录实体类>
 * <详细描述>
 * @author   FengTian
 * @version  
 * @since
 * @see
 */
public class NegotiationReport {
    /**
     * 主键
     */
    private String id;
    
    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 供应商ID
     */
    private String supplierId;
    
    /**
     * 评审时间
     */
    private Date reviewTime;
    
    /**
     * 评审地点
     */
    private String reviewSite;
    
    /**
     * 最终报价
     */
    private BigDecimal finalOffer;
    
    /**
     * 谈判情况
     */
    private String talks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public String getReviewSite() {
        return reviewSite;
    }

    public void setReviewSite(String reviewSite) {
        this.reviewSite = reviewSite;
    }

    public BigDecimal getFinalOffer() {
        return finalOffer;
    }

    public void setFinalOffer(BigDecimal finalOffer) {
        this.finalOffer = finalOffer;
    }

    public String getTalks() {
        return talks;
    }

    public void setTalks(String talks) {
        this.talks = talks;
    }

    
}
