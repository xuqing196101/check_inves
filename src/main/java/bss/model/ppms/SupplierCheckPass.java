package bss.model.ppms;

import java.util.Date;

import ses.model.sms.Supplier;

public class SupplierCheckPass {
    
    /**
     * 供应商
     */
    private Supplier supplier;
    
    
    
    
    
    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
     * </pre>
     */
    private String id;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
     * </pre>
     */
    private Date joinTime;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS
     * </pre>
     */
    private Short bidStatus;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE
     * </pre>
     */
    private Long totalPrice;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
     * </pre>
     */
    private Short totalScore;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
     * </pre>
     */
    private String packageId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
     * </pre>
     */
    private Short isWonBid;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
     * </pre>
     */
    private Short isSendNotice;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
     * </pre>
     *
     * @param id
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME：null
     */
    public Date getJoinTime() {
        return joinTime;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
     * </pre>
     *
     * @param joinTime
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME：null
     */
    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID：null
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID：null
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS：null
     */
    public Short getBidStatus() {
        return bidStatus;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS
     * </pre>
     *
     * @param bidStatus
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS：null
     */
    public void setBidStatus(Short bidStatus) {
        this.bidStatus = bidStatus;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE：null
     */
    public Long getTotalPrice() {
        return totalPrice;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE
     * </pre>
     *
     * @param totalPrice
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE：null
     */
    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE：null
     */
    public Short getTotalScore() {
        return totalScore;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
     * </pre>
     *
     * @param totalScore
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE：null
     */
    public void setTotalScore(Short totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID：null
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
     * </pre>
     *
     * @param packageId
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID：null
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID：null
     */
    public Short getIsWonBid() {
        return isWonBid;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
     * </pre>
     *
     * @param isWonBid
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID：null
     */
    public void setIsWonBid(Short isWonBid) {
        this.isWonBid = isWonBid;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE：null
     */
    public Short getIsSendNotice() {
        return isSendNotice;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
     * </pre>
     *
     * @param isSendNotice
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE：null
     */
    public void setIsSendNotice(Short isSendNotice) {
        this.isSendNotice = isSendNotice;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT：null
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT：null
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT：null
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT：null
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return Returns the supplier.
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier The supplier to set.
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
    
    
}