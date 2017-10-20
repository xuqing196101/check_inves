package extract.model.supplier;

import java.util.List;

public class ProjectVoiceResult {

	private String projectId;
	private String recordId;
	private List<SupplierVoiceResult> suppliers;
	public ProjectVoiceResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProjectVoiceResult(String projectId, String recordId,
			List<SupplierVoiceResult> suppliers) {
		super();
		this.projectId = projectId;
		this.recordId = recordId;
		this.suppliers = suppliers;
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
	public List<SupplierVoiceResult> getSuppliers() {
		return suppliers;
	}
	public void setSuppliers(List<SupplierVoiceResult> suppliers) {
		this.suppliers = suppliers;
	}
	
	
	
}
