package yggc.model.sms;

public class SupplierFsInfoWithBLOBs extends SupplierFsInfo {
    /**
     * <pre>
     * 国内供货业绩
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLY_LEVEL
     * </pre>
     */
    private String supplyLevel;

    /**
     * <pre>
     * 企业简介
     * 表字段 : T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REMARK
     * </pre>
     */
    private String supplierRemark;

    /**
     * <pre>
     * 获取：国内供货业绩
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLY_LEVEL
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLY_LEVEL：国内供货业绩
     */
    public String getSupplyLevel() {
        return supplyLevel;
    }

    /**
     * <pre>
     * 设置：国内供货业绩
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLY_LEVEL
     * </pre>
     *
     * @param supplyLevel
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLY_LEVEL：国内供货业绩
     */
    public void setSupplyLevel(String supplyLevel) {
        this.supplyLevel = supplyLevel == null ? null : supplyLevel.trim();
    }

    /**
     * <pre>
     * 获取：企业简介
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REMARK
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REMARK：企业简介
     */
    public String getSupplierRemark() {
        return supplierRemark;
    }

    /**
     * <pre>
     * 设置：企业简介
     * 表字段：T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REMARK
     * </pre>
     *
     * @param supplierRemark
     *            T_SES_SMS_SUPPLIER_FS_INFO.SUPPLIER_REMARK：企业简介
     */
    public void setSupplierRemark(String supplierRemark) {
        this.supplierRemark = supplierRemark == null ? null : supplierRemark.trim();
    }
}