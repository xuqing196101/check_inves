package bss.model.prms;

import java.math.BigDecimal;

public class ExpertScore {
    private String id;

    private String expertId;

    private String projectId;

    private String packageId;

    private String supplierId;

    private String scoreModelId;

    private BigDecimal expertValue;

    private BigDecimal score;

    private Short isHistory;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId == null ? null : expertId.trim();
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

    public BigDecimal getExpertValue() {
        return expertValue;
    }

    public void setExpertValue(BigDecimal expertValue) {
        this.expertValue = expertValue;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Short getIsHistory() {
        return isHistory;
    }

    public void setIsHistory(Short isHistory) {
        this.isHistory = isHistory;
    }
}