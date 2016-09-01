package yggc.model.sms;

import java.util.Date;

public class SupplierFinance {
    /**
     * <pre>
     * 主键
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.ID
     * </pre>
     */
    private Long id;

    /**
     * <pre>
     * 会计事务所名称
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.NAME
     * </pre>
     */
    private String name;

    /**
     * <pre>
     * 年份
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.YEAR
     * </pre>
     */
    private Short year;

    /**
     * <pre>
     * 事务所联系电话
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.MOBILE
     * </pre>
     */
    private Long mobile;

    /**
     * <pre>
     * 审计人姓名
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.AUDITORS
     * </pre>
     */
    private String auditors;

    /**
     * <pre>
     * 指标
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.INDEX
     * </pre>
     */
    private String index;

    /**
     * <pre>
     * 资产总额
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS
     * </pre>
     */
    private Long totalAssets;

    /**
     * <pre>
     * 负债总额
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES
     * </pre>
     */
    private Long totalLiabilities;

    /**
     * <pre>
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS
     * </pre>
     */
    private Long totalNetAssets;

    /**
     * <pre>
     * 营业收入
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TAKING
     * </pre>
     */
    private Long taking;

    /**
     * <pre>
     * 近三年财务审计报告的审计意见
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION
     * </pre>
     */
    private String auditOpinion;

    /**
     * <pre>
     * 资产负债表 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST
     * </pre>
     */
    private String liabilitiesList;

    /**
     * <pre>
     * 近三年利润表 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST
     * </pre>
     */
    private String profitList;

    /**
     * <pre>
     * 近三年现金流量表 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT
     * </pre>
     */
    private String cashFlowStatement;

    /**
     * <pre>
     * 所有者权益变动表 上传
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST
     * </pre>
     */
    private String changeList;

    /**
     * <pre>
     * 供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID
     * </pre>
     */
    private Long supplierId;

    /**
     * <pre>
     * 创建时间格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 更新时间格式年月日时分秒
     * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 获取：主键
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.ID：主键
     */
    public Long getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：主键
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.ID
     * </pre>
     *
     * @param id
     *            T_SES_SMS_SUPPLIER_FINANCE.ID：主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <pre>
     * 获取：会计事务所名称
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.NAME
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.NAME：会计事务所名称
     */
    public String getName() {
        return name;
    }

    /**
     * <pre>
     * 设置：会计事务所名称
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.NAME
     * </pre>
     *
     * @param name
     *            T_SES_SMS_SUPPLIER_FINANCE.NAME：会计事务所名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * <pre>
     * 获取：年份
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.YEAR
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.YEAR：年份
     */
    public Short getYear() {
        return year;
    }

    /**
     * <pre>
     * 设置：年份
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.YEAR
     * </pre>
     *
     * @param year
     *            T_SES_SMS_SUPPLIER_FINANCE.YEAR：年份
     */
    public void setYear(Short year) {
        this.year = year;
    }

    /**
     * <pre>
     * 获取：事务所联系电话
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.MOBILE
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.MOBILE：事务所联系电话
     */
    public Long getMobile() {
        return mobile;
    }

    /**
     * <pre>
     * 设置：事务所联系电话
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.MOBILE
     * </pre>
     *
     * @param mobile
     *            T_SES_SMS_SUPPLIER_FINANCE.MOBILE：事务所联系电话
     */
    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    /**
     * <pre>
     * 获取：审计人姓名
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.AUDITORS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.AUDITORS：审计人姓名
     */
    public String getAuditors() {
        return auditors;
    }

    /**
     * <pre>
     * 设置：审计人姓名
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.AUDITORS
     * </pre>
     *
     * @param auditors
     *            T_SES_SMS_SUPPLIER_FINANCE.AUDITORS：审计人姓名
     */
    public void setAuditors(String auditors) {
        this.auditors = auditors == null ? null : auditors.trim();
    }

    /**
     * <pre>
     * 获取：指标
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.INDEX
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.INDEX：指标
     */
    public String getIndex() {
        return index;
    }

    /**
     * <pre>
     * 设置：指标
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.INDEX
     * </pre>
     *
     * @param index
     *            T_SES_SMS_SUPPLIER_FINANCE.INDEX：指标
     */
    public void setIndex(String index) {
        this.index = index == null ? null : index.trim();
    }

    /**
     * <pre>
     * 获取：资产总额
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS：资产总额
     */
    public Long getTotalAssets() {
        return totalAssets;
    }

    /**
     * <pre>
     * 设置：资产总额
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS
     * </pre>
     *
     * @param totalAssets
     *            T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS：资产总额
     */
    public void setTotalAssets(Long totalAssets) {
        this.totalAssets = totalAssets;
    }

    /**
     * <pre>
     * 获取：负债总额
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES：负债总额
     */
    public Long getTotalLiabilities() {
        return totalLiabilities;
    }

    /**
     * <pre>
     * 设置：负债总额
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES
     * </pre>
     *
     * @param totalLiabilities
     *            T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES：负债总额
     */
    public void setTotalLiabilities(Long totalLiabilities) {
        this.totalLiabilities = totalLiabilities;
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS：null
     */
    public Long getTotalNetAssets() {
        return totalNetAssets;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS
     * </pre>
     *
     * @param totalNetAssets
     *            T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS：null
     */
    public void setTotalNetAssets(Long totalNetAssets) {
        this.totalNetAssets = totalNetAssets;
    }

    /**
     * <pre>
     * 获取：营业收入
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TAKING
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.TAKING：营业收入
     */
    public Long getTaking() {
        return taking;
    }

    /**
     * <pre>
     * 设置：营业收入
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.TAKING
     * </pre>
     *
     * @param taking
     *            T_SES_SMS_SUPPLIER_FINANCE.TAKING：营业收入
     */
    public void setTaking(Long taking) {
        this.taking = taking;
    }

    /**
     * <pre>
     * 获取：近三年财务审计报告的审计意见
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION：近三年财务审计报告的审计意见
     */
    public String getAuditOpinion() {
        return auditOpinion;
    }

    /**
     * <pre>
     * 设置：近三年财务审计报告的审计意见
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION
     * </pre>
     *
     * @param auditOpinion
     *            T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION：近三年财务审计报告的审计意见
     */
    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    /**
     * <pre>
     * 获取：资产负债表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST：资产负债表 上传
     */
    public String getLiabilitiesList() {
        return liabilitiesList;
    }

    /**
     * <pre>
     * 设置：资产负债表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST
     * </pre>
     *
     * @param liabilitiesList
     *            T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST：资产负债表 上传
     */
    public void setLiabilitiesList(String liabilitiesList) {
        this.liabilitiesList = liabilitiesList == null ? null : liabilitiesList.trim();
    }

    /**
     * <pre>
     * 获取：近三年利润表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST：近三年利润表 上传
     */
    public String getProfitList() {
        return profitList;
    }

    /**
     * <pre>
     * 设置：近三年利润表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST
     * </pre>
     *
     * @param profitList
     *            T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST：近三年利润表 上传
     */
    public void setProfitList(String profitList) {
        this.profitList = profitList == null ? null : profitList.trim();
    }

    /**
     * <pre>
     * 获取：近三年现金流量表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT：近三年现金流量表 上传
     */
    public String getCashFlowStatement() {
        return cashFlowStatement;
    }

    /**
     * <pre>
     * 设置：近三年现金流量表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT
     * </pre>
     *
     * @param cashFlowStatement
     *            T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT：近三年现金流量表 上传
     */
    public void setCashFlowStatement(String cashFlowStatement) {
        this.cashFlowStatement = cashFlowStatement == null ? null : cashFlowStatement.trim();
    }

    /**
     * <pre>
     * 获取：所有者权益变动表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST：所有者权益变动表 上传
     */
    public String getChangeList() {
        return changeList;
    }

    /**
     * <pre>
     * 设置：所有者权益变动表 上传
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST
     * </pre>
     *
     * @param changeList
     *            T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST：所有者权益变动表 上传
     */
    public void setChangeList(String changeList) {
        this.changeList = changeList == null ? null : changeList.trim();
    }

    /**
     * <pre>
     * 获取：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public Long getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商ID T_SES_SMS_SUPPLIER_INFO
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID：供应商ID T_SES_SMS_SUPPLIER_INFO
     */
    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    /**
     * <pre>
     * 获取：创建时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT：创建时间格式年月日时分秒
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT：创建时间格式年月日时分秒
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT
     * </pre>
     *
     * @return T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT：更新时间格式年月日时分秒
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：更新时间格式年月日时分秒
     * 表字段：T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT：更新时间格式年月日时分秒
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}