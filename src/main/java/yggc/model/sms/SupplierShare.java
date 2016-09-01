package yggc.model.sms;

import java.util.Date;

public class SupplierShare {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.ID
     * </pre>
     */
    private Long id;

    /**
     * <pre>
     * 供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID
     * </pre>
     */
    private Long supplierId;

    /**
     * <pre>
     * 出资人名称或姓名
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.NAME
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 出资人性质
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.NATURE
     * </pre>
     */
    private String nature;

    /**
     * <pre>
     * 统一社会信用代码或身份证号码
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.IDENTITY
     * </pre>
     */
    private String identity;

    /**
     * <pre>
     * 出资金额或股份
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.SHARE
     * </pre>
     */
    private String share;

    /**
     * <pre>
     * 比例
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.PROPORTION
     * </pre>
     */
    private String proportion;

    /**
     * <pre>
     * 创建时间 格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间 格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.ID：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_SHARE.ID：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_SES_SMS_SUPPLIER_SHARE.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * <pre>
     * 获取：出资人名称或姓名
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.NAME：出资人名称或姓名
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 设置：出资人名称或姓名
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.NAME
     * </pre>
     *
     * @param name
     *            T_SES_SMS_SUPPLIER_SHARE.NAME：出资人名称或姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * <pre>
     * 获取：出资人性质
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.NATURE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.NATURE：出资人性质
     */
    public String getNature() {
        return nature;
    }

    /**
     * <pre>
     * 设置：出资人性质
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.NATURE
     * </pre>
     *
     * @param nature
     *            T_SES_SMS_SUPPLIER_SHARE.NATURE：出资人性质
     */
    public void setNature(String nature) {
        this.nature = nature == null ? null : nature.trim();
    }

    /**
     * <pre>
     * 获取：统一社会信用代码或身份证号码
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.IDENTITY
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.IDENTITY：统一社会信用代码或身份证号码
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * <pre>
     * 设置：统一社会信用代码或身份证号码
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.IDENTITY
     * </pre>
     *
     * @param identity
     *            T_SES_SMS_SUPPLIER_SHARE.IDENTITY：统一社会信用代码或身份证号码
     */
    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    /**
     * <pre>
     * 获取：出资金额或股份
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.SHARE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.SHARE：出资金额或股份
     */
    public String getShare() {
        return share;
    }

    /**
     * <pre>
     * 设置：出资金额或股份
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.SHARE
     * </pre>
     *
     * @param share
     *            T_SES_SMS_SUPPLIER_SHARE.SHARE：出资金额或股份
     */
    public void setShare(String share) {
        this.share = share == null ? null : share.trim();
    }

    /**
     * <pre>
     * 获取：比例
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.PROPORTION
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.PROPORTION：比例
     */
    public String getProportion() {
        return proportion;
    }

    /**
     * <pre>
     * 设置：比例
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.PROPORTION
     * </pre>
     *
     * @param proportion
     *            T_SES_SMS_SUPPLIER_SHARE.PROPORTION：比例
     */
    public void setProportion(String proportion) {
        this.proportion = proportion == null ? null : proportion.trim();
    }

    /**
     * <pre>
     * 获取：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.CREATED_AT：创建时间 格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_SHARE.CREATED_AT：创建时间 格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT：更新时间 格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间 格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_SHARE.UPDATED_AT：更新时间 格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}