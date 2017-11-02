package extract.model.supplier;

import java.util.List;

public class ProjectVoiceResult {

	private String projectId;
	private String recordId;
	private List<SupplierVoiceResult> supplierResult;
	public ProjectVoiceResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectVoiceResult(String projectId, String recordId,
			List<SupplierVoiceResult> suppliers) {
		super();
		this.projectId = projectId;
		this.recordId = recordId;
		this.supplierResult = suppliers;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public List<SupplierVoiceResult> getSupplierResult() {
		return supplierResult;
	}
	public void setSupplierResult(List<SupplierVoiceResult> supplierResult) {
		this.supplierResult = supplierResult;
	}
}
