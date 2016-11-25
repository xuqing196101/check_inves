package bss.model.ppms;

import java.io.Serializable;
import java.util.Date;

/**
 * 版权：(C) 版权所有 
 * <简述>供应商投标时填写的初审项值
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public class FirstAuditQuota implements Serializable{

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * @Fields packFirstId : TODO(目的和意义)初审项与包关联id
     */
    private String packFirstId;

    /**
     * @Fields packageId : TODO(目的和意义)包id
     */
    private String packageId;

    /**
     * @Fields projectId : TODO(目的和意义)项目id
     */
    private String projectId;

    /**
     * @Fields supplierId : TODO(目的和意义)供应商id
     */
    private String supplierId;

    /**
     * @Fields value : TODO(目的和意义)供应商填写的值，初审项只有满足和不满足两个选择，1：满足 0：不满足
     */
    private Integer value;

    /**
     * @Fields page : TODO(目的和意义)绑定指标的页码
     */
    private Integer page;

    private Date createdAt;

    private Date updatedAt;

    private Short isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPackFirstId() {
        return packFirstId;
    }

    public void setPackFirstId(String packFirstId) {
        this.packFirstId = packFirstId == null ? null : packFirstId.trim();
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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
}