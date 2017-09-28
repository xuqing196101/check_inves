package ses.model.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.model.UploadFile;

public class SupplierFinance implements Serializable {
	private static final long serialVersionUID = -8432362368196241325L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.ID
	 * </pre>
	 */
	private String id;

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
	private String year;

	/**
	 * <pre>
	 * 事务所联系电话
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.MOBILE
	 * </pre>
	 */
	private String telephone;

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
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.QUOTA
	 * </pre>
	 */
	private String quota;

	/**
	 * <pre>
	 * 资产总额
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_ASSETS
	 * </pre>
	 */
	private BigDecimal totalAssets;

	/**
	 * <pre>
	 * 负债总额
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_LIABILITIES
	 * </pre>
	 */
	private BigDecimal totalLiabilities;

	/**
	 * <pre>
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TOTAL_NET_ASSETS
	 * 净资产
	 * </pre>
	 */
	private BigDecimal totalNetAssets;

	/**
	 * <pre>
	 * 营业收入
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.TAKING
	 * </pre>
	 */
	private BigDecimal taking;

	/**
	 * <pre>
	 * 近三年财务审计报告书中的审计报告
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.AUDIT_OPINION
	 * </pre>
	 */
	private String auditOpinion;

	private String auditOpinionId;

	/**
	 * <pre>
	 * 资产负债表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.LIABILITIES_LIST
	 * </pre>
	 */
	private String liabilitiesList;

	private String liabilitiesListId;

	/**
	 * <pre>
	 * 近三年利润表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.PROFIT_LIST
	 * </pre>
	 */
	private String profitList;

	private String profitListId;

	/**
	 * <pre>
	 * 近三年现金流量表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CASH_FLOW_STATEMENT
	 * </pre>
	 */
	private String cashFlowStatement;

	private String cashFlowStatementId;

	/**
	 * <pre>
	 * 所有者权益变动表 上传
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CHANGE_LIST
	 * </pre>
	 */
	private String changeList;

	private String changeListId;

	/**
	 * <pre>
	 * 供应商ID T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 创建时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.CREATED_AT
	 * </pre>
	 */
	private Timestamp createdAt;

	/**
	 * <pre>
	 * 更新时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_FINANCE.UPDATED_AT
	 * </pre>
	 */
	private Timestamp updatedAt;

	private Integer sign;

	private List<UploadFile> listUploadFiles = new ArrayList<UploadFile>();

	private String threeYear;
	/**增加删除 和审核状态 和文件名称*/
	private Integer isDeleted;
	private Integer status;
	private String fileName;
	
	public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAuditors() {
		return auditors;
	}

	public void setAuditors(String auditors) {
		this.auditors = auditors;
	}

	public String getQuota() {
		return quota;
	}

	public void setQuota(String quota) {
		this.quota = quota;
	}

	public BigDecimal getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(BigDecimal totalAssets) {
		this.totalAssets = totalAssets;
	}

	public BigDecimal getTotalLiabilities() {
		return totalLiabilities;
	}

	public void setTotalLiabilities(BigDecimal totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}

	public BigDecimal getTotalNetAssets() {
		return totalNetAssets;
	}

	public void setTotalNetAssets(BigDecimal totalNetAssets) {
		this.totalNetAssets = totalNetAssets;
	}

	public BigDecimal getTaking() {
		return taking;
	}

	public void setTaking(BigDecimal taking) {
		this.taking = taking;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public String getLiabilitiesList() {
		return liabilitiesList;
	}

	public void setLiabilitiesList(String liabilitiesList) {
		this.liabilitiesList = liabilitiesList;
	}

	public String getProfitList() {
		return profitList;
	}

	public void setProfitList(String profitList) {
		this.profitList = profitList;
	}

	public String getCashFlowStatement() {
		return cashFlowStatement;
	}

	public void setCashFlowStatement(String cashFlowStatement) {
		this.cashFlowStatement = cashFlowStatement;
	}

	public String getChangeList() {
		return changeList;
	}

	public void setChangeList(String changeList) {
		this.changeList = changeList;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	

	public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public List<UploadFile> getListUploadFiles() {
		return listUploadFiles;
	}

	public void setListUploadFiles(List<UploadFile> listUploadFiles) {
		this.listUploadFiles = listUploadFiles;
	}

	public String getAuditOpinionId() {
		return auditOpinionId;
	}

	public void setAuditOpinionId(String auditOpinionId) {
		this.auditOpinionId = auditOpinionId;
	}

	public String getLiabilitiesListId() {
		return liabilitiesListId;
	}

	public void setLiabilitiesListId(String liabilitiesListId) {
		this.liabilitiesListId = liabilitiesListId;
	}

	public String getProfitListId() {
		return profitListId;
	}

	public void setProfitListId(String profitListId) {
		this.profitListId = profitListId;
	}

	public String getCashFlowStatementId() {
		return cashFlowStatementId;
	}

	public void setCashFlowStatementId(String cashFlowStatementId) {
		this.cashFlowStatementId = cashFlowStatementId;
	}

	public String getChangeListId() {
		return changeListId;
	}

	public void setChangeListId(String changeListId) {
		this.changeListId = changeListId;
	}

	public String getThreeYear() {
		return threeYear;
	}

	public void setThreeYear(String threeYear) {
		this.threeYear = threeYear;
	}

	
}