package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplierMatPro implements Serializable {
	private static final long serialVersionUID = 740550258221091614L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID  T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 组织机构
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.ORG_NAME
	 * </pre>
	 */
	private String orgName;

	/**
	 * <pre>
	 * 人员总数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_PERSON
	 * </pre>
	 */
	private Integer totalPerson;

	/**
	 * <pre>
	 * 管理人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_MANGE
	 * </pre>
	 */
	private Integer totalMange;

	/**
	 * <pre>
	 * 技术人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_TECH
	 * </pre>
	 */
	private Integer totalTech;

	/**
	 * <pre>
	 * 工人
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_WORKER
	 * </pre>
	 */
	private Integer totalWorker;

	/**
	 * <pre>
	 * 技术人员比例
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.SCALE_TECH
	 * </pre>
	 */
	private String scaleTech;

	/**
	 * <pre>
	 * 高级技术人员比例
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.SCALE_HEIGHT_TECH
	 * </pre>
	 */
	private String scaleHeightTech;

	/**
	 * <pre>
	 * 研发部名称
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.RESEARCH_NAME
	 * </pre>
	 */
	private String researchName;

	/**
	 * <pre>
	 * 研发部人数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_RESEARCH
	 * </pre>
	 */
	private Integer totalResearch;

	/**
	 * <pre>
	 * 研发部人数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.RESEARCH_LEAD
	 * </pre>
	 */
	private String researchLead;

	/**
	 * <pre>
	 * 承担国家军队科研项目
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.COUNTRY_PRO
	 * </pre>
	 */
	private String countryPro;

	/**
	 * <pre>
	 * 获得国家军队奖励
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.COUNTRY_REWARD
	 * </pre>
	 */
	private String countryReward;

	/**
	 * <pre>
	 * 生产线数量
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_BELTLINE
	 * </pre>
	 */
	private Integer totalBeltline;

	/**
	 * <pre>
	 * 生产设备数量
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_DEVICE
	 * </pre>
	 */
	private Integer totalDevice;

	/**
	 * <pre>
	 * 质检部名称
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.QC_NAME
	 * </pre>
	 */
	private String qcName;

	/**
	 * <pre>
	 * 质检部人数
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.TOTAL_QC
	 * </pre>
	 */
	private Integer totalQc;

	/**
	 * <pre>
	 * 质检部负责人
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.QC_LEAD
	 * </pre>
	 */
	private String qcLead;

	/**
	 * <pre>
	 * 质量检测设备名称
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.QC_DEVICE
	 * </pre>
	 */
	private String qcDevice;

	/**
	 * <pre>
	 * 创建时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_PRO.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;

	private List<SupplierCertPro> listSupplierCertPros = new ArrayList<SupplierCertPro>();

	private List<SupplierItemsPro> listSupplierItemsPros = new ArrayList<SupplierItemsPro>();

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

	public String getScaleTech() {
		return scaleTech;
	}

	public void setScaleTech(String scaleTech) {
		this.scaleTech = scaleTech;
	}

	public String getScaleHeightTech() {
		return scaleHeightTech;
	}

	public void setScaleHeightTech(String scaleHeightTech) {
		this.scaleHeightTech = scaleHeightTech;
	}

	public String getResearchName() {
		return researchName;
	}

	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}

	public Integer getTotalResearch() {
		return totalResearch;
	}

	public void setTotalResearch(Integer totalResearch) {
		this.totalResearch = totalResearch;
	}

	public String getCountryPro() {
		return countryPro;
	}

	public void setCountryPro(String countryPro) {
		this.countryPro = countryPro;
	}

	public String getCountryReward() {
		return countryReward;
	}

	public void setCountryReward(String countryReward) {
		this.countryReward = countryReward;
	}

	public Integer getTotalBeltline() {
		return totalBeltline;
	}

	public void setTotalBeltline(Integer totalBeltline) {
		this.totalBeltline = totalBeltline;
	}

	public Integer getTotalDevice() {
		return totalDevice;
	}

	public void setTotalDevice(Integer totalDevice) {
		this.totalDevice = totalDevice;
	}

	public String getQcName() {
		return qcName;
	}

	public void setQcName(String qcName) {
		this.qcName = qcName;
	}

	public Integer getTotalQc() {
		return totalQc;
	}

	public void setTotalQc(Integer totalQc) {
		this.totalQc = totalQc;
	}

	public String getQcLead() {
		return qcLead;
	}

	public void setQcLead(String qcLead) {
		this.qcLead = qcLead;
	}

	public String getQcDevice() {
		return qcDevice;
	}

	public void setQcDevice(String qcDevice) {
		this.qcDevice = qcDevice;
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

	public String getResearchLead() {
		return researchLead;
	}

	public void setResearchLead(String researchLead) {
		this.researchLead = researchLead;
	}

	public List<SupplierCertPro> getListSupplierCertPros() {
		return listSupplierCertPros;
	}

	public void setListSupplierCertPros(List<SupplierCertPro> listSupplierCertPros) {
		this.listSupplierCertPros = listSupplierCertPros;
	}

	public List<SupplierItemsPro> getListSupplierItemsPros() {
		return listSupplierItemsPros;
	}

	public void setListSupplierItemsPros(List<SupplierItemsPro> listSupplierItemsPros) {
		this.listSupplierItemsPros = listSupplierItemsPros;
	}
}