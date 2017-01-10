package bss.model.ppms;

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
public class Negotiation {
    /**
     * 主键
     */
    private String id;
    
    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 专家ID
     */
    private String expertId;
    
    /**
     * 记录人
     */
    private String nuter;
    
    /**
     * 记录内容
     */
    private String negotiationRecord;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    private String packageId;

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

    public String getExpertId() {
        return expertId;
    }

    public void setExpertId(String expertId) {
        this.expertId = expertId;
    }

    public String getNuter() {
        return nuter;
    }

    public void setNuter(String nuter) {
        this.nuter = nuter;
    }

    public String getNegotiationRecord() {
        return negotiationRecord;
    }

    public void setNegotiationRecord(String negotiationRecord) {
        this.negotiationRecord = negotiationRecord;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }
    
    
    
}
