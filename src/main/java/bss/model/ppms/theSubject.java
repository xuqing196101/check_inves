package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;

public class theSubject {
    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.ID
     * </pre>
     */
    private String id;
    
    /**
     * <pre>
     * 供应商id
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;

    /**
     * <pre>
     * 明细id
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.DETAIL_ID
     * </pre>
     */
    private String detailId;

    /**
     * <pre>
     * 货物名称
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.GOODS_NAME
     * </pre>
     */
    private String goodsName;

    /**
     * <pre>
     * 规格型号
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.STAND
     * </pre>
     */
    private String stand;

    /**
     * <pre>
     * 质量技术标准
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.QUALIT_STAND
     * </pre>
     */
    private String qualitStand;

    /**
     * <pre>
     * 计量单位
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.ITEM
     * </pre>
     */
    private String item;

    /**
     * <pre>
     * 采购数量
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.PURCHASE_COUNT
     * </pre>
     */
    private String purchaseCount;

    /**
     * <pre>
     * 单价
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.UNIT_PRICE
     * </pre>
     */
    private BigDecimal unitPrice;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 包id
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.PACKAGE_ID
     * </pre>
     */
    private String packageId;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_BSS_PPMS_THE_SUBJECT.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_THE_SUBJECT.ID
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_THE_SUBJECT.ID
     * </pre>
     *
     * @param id
     *            T_BSS_PPMS_THE_SUBJECT.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：明细id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.DETAIL_ID
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.DETAIL_ID：明细id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * <pre>
     * 设置：明细id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.DETAIL_ID
     * </pre>
     *
     * @param detailId
     *            T_BSS_PPMS_THE_SUBJECT.DETAIL_ID：明细id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
    }

    /**
     * <pre>
     * 获取：货物名称
     * 表字段：T_BSS_PPMS_THE_SUBJECT.GOODS_NAME
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.GOODS_NAME：货物名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * <pre>
     * 设置：货物名称
     * 表字段：T_BSS_PPMS_THE_SUBJECT.GOODS_NAME
     * </pre>
     *
     * @param goodsName
     *            T_BSS_PPMS_THE_SUBJECT.GOODS_NAME：货物名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    /**
     * <pre>
     * 获取：规格型号
     * 表字段：T_BSS_PPMS_THE_SUBJECT.STAND
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.STAND：规格型号
     */
    public String getStand() {
        return stand;
    }

    /**
     * <pre>
     * 设置：规格型号
     * 表字段：T_BSS_PPMS_THE_SUBJECT.STAND
     * </pre>
     *
     * @param stand
     *            T_BSS_PPMS_THE_SUBJECT.STAND：规格型号
     */
    public void setStand(String stand) {
        this.stand = stand == null ? null : stand.trim();
    }

    /**
     * <pre>
     * 获取：质量技术标准
     * 表字段：T_BSS_PPMS_THE_SUBJECT.QUALIT_STAND
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.QUALIT_STAND：质量技术标准
     */
    public String getQualitStand() {
        return qualitStand;
    }

    /**
     * <pre>
     * 设置：质量技术标准
     * 表字段：T_BSS_PPMS_THE_SUBJECT.QUALIT_STAND
     * </pre>
     *
     * @param qualitStand
     *            T_BSS_PPMS_THE_SUBJECT.QUALIT_STAND：质量技术标准
     */
    public void setQualitStand(String qualitStand) {
        this.qualitStand = qualitStand == null ? null : qualitStand.trim();
    }

    /**
     * <pre>
     * 获取：计量单位
     * 表字段：T_BSS_PPMS_THE_SUBJECT.ITEM
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.ITEM：计量单位
     */
    public String getItem() {
        return item;
    }

    /**
     * <pre>
     * 设置：计量单位
     * 表字段：T_BSS_PPMS_THE_SUBJECT.ITEM
     * </pre>
     *
     * @param item
     *            T_BSS_PPMS_THE_SUBJECT.ITEM：计量单位
     */
    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    /**
     * <pre>
     * 获取：采购数量
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PURCHASE_COUNT
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.PURCHASE_COUNT：采购数量
     */
    public String getPurchaseCount() {
        return purchaseCount;
    }

    /**
     * <pre>
     * 设置：采购数量
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PURCHASE_COUNT
     * </pre>
     *
     * @param purchaseCount
     *            T_BSS_PPMS_THE_SUBJECT.PURCHASE_COUNT：采购数量
     */
    public void setPurchaseCount(String purchaseCount) {
        this.purchaseCount = purchaseCount == null ? null : purchaseCount.trim();
    }

    /**
     * <pre>
     * 获取：单价
     * 表字段：T_BSS_PPMS_THE_SUBJECT.UNIT_PRICE
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.UNIT_PRICE：单价
     */
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    /**
     * <pre>
     * 设置：单价
     * 表字段：T_BSS_PPMS_THE_SUBJECT.UNIT_PRICE
     * </pre>
     *
     * @param unitPrice
     *            T_BSS_PPMS_THE_SUBJECT.UNIT_PRICE：单价
     */
    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_BSS_PPMS_THE_SUBJECT.CREATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_BSS_PPMS_THE_SUBJECT.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_BSS_PPMS_THE_SUBJECT.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：T_BSS_PPMS_THE_SUBJECT.UPDATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.UPDATED_AT：修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：T_BSS_PPMS_THE_SUBJECT.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_BSS_PPMS_THE_SUBJECT.UPDATED_AT：修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：包id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PACKAGE_ID
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.PACKAGE_ID：包id
     */
    public String getPackageId() {
        return packageId;
    }

    /**
     * <pre>
     * 设置：包id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PACKAGE_ID
     * </pre>
     *
     * @param packageId
     *            T_BSS_PPMS_THE_SUBJECT.PACKAGE_ID：包id
     */
    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PROJECT_ID
     * </pre>
     *
     * @return T_BSS_PPMS_THE_SUBJECT.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_BSS_PPMS_THE_SUBJECT.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_BSS_PPMS_THE_SUBJECT.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
    
}