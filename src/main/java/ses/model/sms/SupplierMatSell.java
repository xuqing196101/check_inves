package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierMatSell implements Serializable {
	private static final long serialVersionUID = 2673499646504919702L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID  T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 组织机构
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.ORG_NAME
	 * </pre>
	 */
	private String orgName;

	/**
	 * <pre>
	 * 人员总数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.TOTAL_PERSON
	 * </pre>
	 */
	private Integer totalPerson;

	/**
	 * <pre>
	 * 管理人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.TOTAL_MANGE
	 * </pre>
	 */
	private Integer totalMange;

	/**
	 * <pre>
	 * 技术人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.TOTAL_TECH
	 * </pre>
	 */
	private Integer totalTech;

	/**
	 * <pre>
	 * 工人
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.TOTAL_WORKER
	 * </pre>
	 */
	private Integer totalWorker;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 *  更新时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SELL.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private List<SupplierCertSell> listSupplierCertSells = new ArrayList<SupplierCertSell>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getTotalPerson() {
		return totalPerson;
	}

	public void setTotalPerson(Integer totalPerson) {
		this.totalPerson = totalPerson;
	}

	public Integer getTotalMange() {
		return totalMange;
	}

	public void setTotalMange(Integer totalMange) {
		this.totalMange = totalMange;
	}

	public Integer getTotalTech() {
		return totalTech;
	}

	public void setTotalTech(Integer totalTech) {
		this.totalTech = totalTech;
	}

	public Integer getTotalWorker() {
		return totalWorker;
	}

	public void setTotalWorker(Integer totalWorker) {
		this.totalWorker = totalWorker;
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

	public List<SupplierCertSell> getListSupplierCertSells() {
		return listSupplierCertSells;
	}

	public void setListSupplierCertSells(List<SupplierCertSell> listSupplierCertSells) {
		this.listSupplierCertSells = listSupplierCertSells;
	}

}