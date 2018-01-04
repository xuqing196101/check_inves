package ses.model.sms.review;

/**
 * 产品类别审核表（供应商实地考察用）
 * 
 * T_SES_SMS_SUPPLIER_CATE_AUDIT
 * 
 * @author yggc
 * 
 * @date 2018-01-03
 *
 */
public class SupplierCateAudit {
    /**
     * 主键
     */
    private String id;

    /**
     * 序号
     */
    private String sn;

    /**
     * 供应商ID
     */
    private String supplierId;

    /**
     * 供应商类型
     */
    private String supplierTypeId;

    /**
     * 产品类别ID
     */
    private String categoryId;

    /**
     * 产品类别名称
     */
    private String categoryName;

    /**
     * 是否有供货能力
     */
    private Integer isSupplied;

    /**
     * 理由
     */
    private String suggest;

    /**
     * 是否删除 0 有效 1 删除
     */
    private Integer isDeleted;

    /**
     * 所在位置
     */
    private Integer position;

    /**
     * 主键
     * @return ID 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 主键
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 序号
     * @return SN 序号
     */
    public String getSn() {
        return sn;
    }

    /**
     * 序号
     * @param sn 序号
     */
    public void setSn(String sn) {
        this.sn = sn;
    }

    /**
     * 供应商ID
     * @return SUPPLIER_ID 供应商ID
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * 供应商ID
     * @param supplierId 供应商ID
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * 供应商类型
     * @return SUPPLIER_TYPE_ID 供应商类型
     */
    public String getSupplierTypeId() {
        return supplierTypeId;
    }

    /**
     * 供应商类型
     * @param supplierTypeId 供应商类型
     */
    public void setSupplierTypeId(String supplierTypeId) {
        this.supplierTypeId = supplierTypeId;
    }

    /**
     * 产品类别ID
     * @return CATEGORY_ID 产品类别ID
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 产品类别ID
     * @param categoryId 产品类别ID
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 产品类别名称
     * @return CATEGORY_NAME 产品类别名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 产品类别名称
     * @param categoryName 产品类别名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 是否有供货能力
     * @return IS_SUPPLIED 是否有供货能力
     */
    public Integer getIsSupplied() {
        return isSupplied;
    }

    /**
     * 是否有供货能力
     * @param isSupplied 是否有供货能力
     */
    public void setIsSupplied(Integer isSupplied) {
        this.isSupplied = isSupplied;
    }

    /**
     * 理由
     * @return SUGGEST 理由
     */
    public String getSuggest() {
        return suggest;
    }

    /**
     * 理由
     * @param suggest 理由
     */
    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    /**
     * 是否删除 0 有效 1 删除
     * @return IS_DELETED 是否删除 0 有效 1 删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除 0 有效 1 删除
     * @param isDeleted 是否删除 0 有效 1 删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 所在位置
     * @return POSITION 所在位置
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 所在位置
     * @param position 所在位置
     */
    public void setPosition(Integer position) {
        this.position = position;
    }
}