package bss.model.prms.ext;

public class SupplierExt {
	//包id
	private String packageId;
	//专家id
	private String expertId;
	//供应商id
	private String supplierId;
	//该供应商是否通过评审
	private String suppIsPass;
	//初审是否通过
	private String isAudit;
	
	
	
	
	public String getIsAudit() {
		return isAudit;
	}
	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSuppIsPass() {
		return suppIsPass;
	}
	public void setSuppIsPass(String suppIsPass) {
		this.suppIsPass = suppIsPass;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	
}
