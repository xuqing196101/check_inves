package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierMatServe implements Serializable {
	private static final long serialVersionUID = -9180711450539395236L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 组织机构
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.ORG_NAME
	 * </pre>
	 */
	private String orgName;

	/**
	 * <pre>
	 * 人员总数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.TOTAL_PERSON
	 * </pre>
	 */
	private Integer totalPerson;

	/**
	 * <pre>
	 * 管理人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.TOTAL_MANGE
	 * </pre>
	 */
	private Integer totalMange;

	/**
	 * <pre>
	 * 技术人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.TOTAL_TECH
	 * </pre>
	 */
	private Integer totalTech;

	/**
	 * <pre>
	 * 工人
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.TOTAL_WORKER
	 * </pre>
	 */
	private Integer totalWorker;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_SE.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private List<SupplierCertServe> listSupplierCertSes = new ArrayList<SupplierCertServe>();

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

	public List<SupplierCertServe> getListSupplierCertSes() {
		return listSupplierCertSes;
	}

	public void setListSupplierCertSes(List<SupplierCertServe> listSupplierCertSes) {
		this.listSupplierCertSes = listSupplierCertSes;
	}
}