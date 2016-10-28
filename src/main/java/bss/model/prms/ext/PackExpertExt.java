package bss.model.prms.ext;

import java.util.List;

import bss.model.prms.PackageExpert;
import ses.model.ems.Expert;

public class PackExpertExt {
	//专家集合
	private Expert expert;
	//关联表集合
	private List<PackageExpert> packExpertList;
	//包id
	private String packageId;
	//项目id
	private String projectId;

	//符合性审查是否完成
	private String isPass;



	public String getIsPass() {
		return isPass;
	}

	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	public Expert getExpert() {
		return expert;
	}

	public void setExpert(Expert expert) {
		this.expert = expert;
	}

	public List<PackageExpert> getPackExpertList() {
		return packExpertList;
	}

	public void setPackExpertList(List<PackageExpert> packExpertList) {
		this.packExpertList = packExpertList;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}
