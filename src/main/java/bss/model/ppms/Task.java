package bss.model.ppms;

import java.util.Date;
import java.util.List;

public class Task {
	private String id;

    private String name;

    private String purchaseId;

    private String documentNumber;

    private Date giveTime;
    
    private Integer status;
    
    private String procurementMethod;
    
    private String purchaseRequiredId;
    
    private Integer isDeleted;
    
    private String materialsType;
    
    private Date year;
    
    private Project project;
    
    private String collectId;

	public Task(String id) {
		super();
		this.id = id;
	}
	
	public Task(){
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Date getGiveTime() {
		return giveTime;
	}

	public void setGiveTime(Date giveTime) {
		this.giveTime = giveTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getProcurementMethod() {
		return procurementMethod;
	}

	public void setProcurementMethod(String procurementMethod) {
		this.procurementMethod = procurementMethod;
	}

	public String getPurchaseRequiredId() {
		return purchaseRequiredId;
	}

	public void setPurchaseRequiredId(String purchaseRequiredId) {
		this.purchaseRequiredId = purchaseRequiredId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getMaterialsType() {
		return materialsType;
	}

	public void setMaterialsType(String materialsType) {
		this.materialsType = materialsType;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	

   
}
