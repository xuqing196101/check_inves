package bss.model.prms.ext;

import java.util.ArrayList;
import java.util.List;

import bss.model.prms.FirstAudit;
import ses.model.sms.Supplier;

/**
 * 
  * <p>Title:Extension </p>
  * <p>Description: </p>封装信息 扩展类
  * <p>Company: yggc </p> 
  * @author ShaoYangYang
  * @date 2016年10月20日下午7:48:20
 */
public class Extension {
	//项目id
	private String projectId;
	//项目名称
	private String projectName;
	//项目编号
	private String projectCode;
	//包id
	private String packageId;
	//包名
	private String packageName;
	//初审项集合
	private List<FirstAudit> firstAuditList = new ArrayList<>();
	//供应商集合
	private List<Supplier> supplierList = new ArrayList<>();
	
	
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public List<FirstAudit> getFirstAuditList() {
		return firstAuditList;
	}
	public void setFirstAuditList(List<FirstAudit> firstAuditList) {
		this.firstAuditList = firstAuditList;
	}
	public List<Supplier> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}
	
}
