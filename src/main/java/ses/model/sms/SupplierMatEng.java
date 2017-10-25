package ses.model.sms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ses.model.bms.Area;

public class SupplierMatEng implements Serializable {
	private static final long serialVersionUID = -6683954164102872656L;

	/**
	 * <pre>
	 * 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 供应商ID  T_SES_SMS_SUPPLIER_INFO
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.SUPPLIER_ID
	 * </pre>
	 */
	private String supplierId;

	/**
	 * <pre>
	 * 组织机构
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.ORG_NAME
	 * </pre>
	 */
	private String orgName;

	/**
	 * <pre>
	 * 技术人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.TOTAL_TECH
	 * </pre>
	 */
	private Integer totalTech;

	/**
	 * <pre>
	 * 中级及以上职称人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.TOTAL_GL_NORMAL
	 * </pre>
	 */
	private Integer totalGlNormal;

	/**
	 * <pre>
	 * 管理人员
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.TOTAL_MANGE
	 * </pre>
	 */
	private Integer totalMange;

	/**
	 * <pre>
	 * 国家或军队保密工程业绩
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.CONFIDENTIAL_ACHIEVEMENT
	 * </pre>
	 */
	private String confidentialAchievement;
	
	/**
	 * <pre>
	 * 是否有国家或军队保密工程业绩  0无  1有
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.IS_HAVING_CON_ACHI
	 * </pre>
	 */
	private String isHavingConAchi;
	
	/**
     * <pre>
     * 技术工人
     * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.
     * </pre>
     */
    private Integer totalTechWorker;

	/**
	 * <pre>
	 * 创建时间格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.CREATED_AT
	 * </pre>
	 */
	private Date createdAt;

	/**
	 * <pre>
	 * 更新时间 格式年月日时分秒
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.UPDATED_AT
	 * </pre>
	 */
	private Date updatedAt;
	
	/**
	 * <pre>
	 * 承揽业务范围
	 * 表字段 : T_SES_SMS_SUPPLIER_MAT_ENG.BUSINESS_SCOPE
	 * </pre>
	 */
	private String businessScope;
	
	private List<Area> businessScopeAreas = new ArrayList<Area>();

	private List<SupplierCertEng> listSupplierCertEngs = new ArrayList<SupplierCertEng>();

	private List<SupplierAptitute> listSupplierAptitutes = new ArrayList<SupplierAptitute>();

	private List<SupplierRegPerson> listSupplierRegPersons = new ArrayList<SupplierRegPerson>();

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

	public Integer getTotalTech() {
		return totalTech;
	}

	public void setTotalTech(Integer totalTech) {
		this.totalTech = totalTech;
	}

	public Integer getTotalGlNormal() {
		return totalGlNormal;
	}

	public void setTotalGlNormal(Integer totalGlNormal) {
		this.totalGlNormal = totalGlNormal;
	}

	public Integer getTotalMange() {
		return totalMange;
	}

	public void setTotalMange(Integer totalMange) {
		this.totalMange = totalMange;
	}

	public Integer getTotalTechWorker() {
		return totalTechWorker;
	}

	public void setTotalTechWorker(Integer totalTechWorker) {
		this.totalTechWorker = totalTechWorker;
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

	public List<SupplierCertEng> getListSupplierCertEngs() {
		return listSupplierCertEngs;
	}

	public void setListSupplierCertEngs(List<SupplierCertEng> listSupplierCertEngs) {
		this.listSupplierCertEngs = listSupplierCertEngs;
	}

	public List<SupplierAptitute> getListSupplierAptitutes() {
		return listSupplierAptitutes;
	}

	public void setListSupplierAptitutes(List<SupplierAptitute> listSupplierAptitutes) {
		this.listSupplierAptitutes = listSupplierAptitutes;
	}

	public List<SupplierRegPerson> getListSupplierRegPersons() {
		return listSupplierRegPersons;
	}

	public void setListSupplierRegPersons(List<SupplierRegPerson> listSupplierRegPersons) {
		this.listSupplierRegPersons = listSupplierRegPersons;
	}

    public String getConfidentialAchievement() {
        return confidentialAchievement;
    }

    public void setConfidentialAchievement(String confidentialAchievement) {
        this.confidentialAchievement = confidentialAchievement;
    }

    public String getIsHavingConAchi() {
        return isHavingConAchi;
    }

    public void setIsHavingConAchi(String isHavingConAchi) {
        this.isHavingConAchi = isHavingConAchi;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

	public List<Area> getBusinessScopeAreas() {
		return businessScopeAreas;
	}

	public void setBusinessScopeAreas(List<Area> businessScopeAreas) {
		this.businessScopeAreas = businessScopeAreas;
	}
    
}