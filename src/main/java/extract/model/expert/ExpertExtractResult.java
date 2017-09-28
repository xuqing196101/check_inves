package extract.model.expert;

import java.io.Serializable;
import java.util.Date;

import ses.model.ems.ExpertHistory;

/**
 * 
 * Description: 专家抽取结果实体
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class ExpertExtractResult extends ExpertHistory implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
    private String id;

    /**
     * 项目信息id
     */
    private String projectId;

    /**
     * 专家id
     */
    private String expertId;

    /**
     * 条件id
     */
    private String conditionId;

    /**
     * 评审时间
     */
    private Date reviewTime;

    /**
     * 是否参加 1 参加 2 待定3不参加
     */
    private Short isJoin;

    /**
     * 不能参加理由
     */
    private String reason;

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

    /**
     * 是否为候补专家
     */
    private Short isAlternate;

    /**
     * 专家类别
     */
    private String expertCode;

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }

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

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId == null ? null : conditionId.trim();
    }

    public Date getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(Date reviewTime) {
        this.reviewTime = reviewTime;
    }

    public Short getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Short isJoin) {
        this.isJoin = isJoin;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
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

    public Short getIsAlternate() {
        return isAlternate;
    }

    public void setIsAlternate(Short isAlternate) {
        this.isAlternate = isAlternate;
    }

}