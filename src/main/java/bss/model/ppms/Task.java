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
    
    private Integer procurementMethod;
    
    private Integer purchaseRequiredId;
    
    private Integer isDeleted;
    
    private List<TaskAttachments> taskAttachment;

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

	public Integer getProcurementMethod() {
		return procurementMethod;
	}

	public void setProcurementMethod(Integer procurementMethod) {
		this.procurementMethod = procurementMethod;
	}

	public Integer getPurchaseRequiredId() {
		return purchaseRequiredId;
	}

	public void setPurchaseRequiredId(Integer purchaseRequiredId) {
		this.purchaseRequiredId = purchaseRequiredId;
	}

	public List<TaskAttachments> getTaskAttachment() {
		return taskAttachment;
	}

	public void setTaskAttachment(List<TaskAttachments> taskAttachment) {
		this.taskAttachment = taskAttachment;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	
  
   
}
