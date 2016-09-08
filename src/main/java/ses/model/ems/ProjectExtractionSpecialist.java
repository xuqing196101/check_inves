package ses.model.ems;

import java.util.Date;

public class ProjectExtractionSpecialist {
    private String id;

    private Long projectId;

    private Long expertExtractRecordId;

    private Long expertId;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getExpertExtractRecordId() {
        return expertExtractRecordId;
    }

    public void setExpertExtractRecordId(Long expertExtractRecordId) {
        this.expertExtractRecordId = expertExtractRecordId;
    }

    public Long getExpertId() {
        return expertId;
    }

    public void setExpertId(Long expertId) {
        this.expertId = expertId;
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