package bss.model.ppms;

import java.util.Date;

public class SaleTender {
    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     */
    private Short statusBid;

    /**
     * <pre>
     * 供应商id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 保证金状态 1缴纳 2，缴纳
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     */
    private Short statusBond;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @param id
     *            T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public Short getStatusBid() {
        return statusBid;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @param statusBid
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public void setStatusBid(Short statusBid) {
        this.statusBid = statusBid;
    }

    /**
     * <pre>
     * 获取：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public Short getStatusBond() {
        return statusBond;
    }

    /**
     * <pre>
     * 设置：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @param statusBond
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public void setStatusBond(Short statusBond) {
        this.statusBond = statusBond;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}