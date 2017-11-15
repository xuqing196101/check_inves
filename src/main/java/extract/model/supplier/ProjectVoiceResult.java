package extract.model.supplier;

import java.util.List;

public class ProjectVoiceResult {

	private String projectId;
	private String recordeId;
	private List<SupplierVoiceResult> supplierResult;

	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<SupplierVoiceResult> getSupplierResult() {
		return supplierResult;
	}
	public void setSupplierResult(List<SupplierVoiceResult> supplierResult) {
		this.supplierResult = supplierResult;
	}
	public String getRecordeId() {
		return recordeId;
	}
	public void setRecordeId(String recordeId) {
		this.recordeId = recordeId;
	}
}
