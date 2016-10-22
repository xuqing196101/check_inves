package bss.model.ppms.ext;

import bss.model.ppms.Project;

public class ProjectExt extends Project {
    //包id
	private String packageId;
	//包名
	private String packageName;
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
	
}
