package bss.model.ppms;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;


public class Task {
    
	private String id;
	
	@NotBlank(message = "任务名称不能为空") 
    private String name; //任务名称
	
    private String purchaseId; //采购部门
	
	@NotBlank(message = "计划编号不能为空") 
    private String documentNumber;

    private Date giveTime;
    
    private Integer status;
    
    private String procurementMethod;
    
    private String purchaseRequiredId;
    
    private Integer isDeleted;
    
    private String materialsType;
    
    private Date year;
    
    private Date acceptTime;
    
    private String collectId;
    
    private String passWord;
    
    private Integer taskNature;
    
    private String orgName;

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

	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Integer getTaskNature() {
		return taskNature;
	}

	public void setTaskNature(Integer taskNature) {
		this.taskNature = taskNature;
	}

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acceptTime == null) ? 0 : acceptTime.hashCode());
		result = prime * result
				+ ((collectId == null) ? 0 : collectId.hashCode());
		result = prime * result
				+ ((documentNumber == null) ? 0 : documentNumber.hashCode());
		result = prime * result
				+ ((giveTime == null) ? 0 : giveTime.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result
				+ ((materialsType == null) ? 0 : materialsType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orgName == null) ? 0 : orgName.hashCode());
		result = prime * result
				+ ((passWord == null) ? 0 : passWord.hashCode());
		result = prime
				* result
				+ ((procurementMethod == null) ? 0 : procurementMethod
						.hashCode());
		result = prime * result
				+ ((purchaseId == null) ? 0 : purchaseId.hashCode());
		result = prime
				* result
				+ ((purchaseRequiredId == null) ? 0 : purchaseRequiredId
						.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((taskNature == null) ? 0 : taskNature.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (acceptTime == null) {
			if (other.acceptTime != null)
				return false;
		} else if (!acceptTime.equals(other.acceptTime))
			return false;
		if (collectId == null) {
			if (other.collectId != null)
				return false;
		} else if (!collectId.equals(other.collectId))
			return false;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber))
			return false;
		if (giveTime == null) {
			if (other.giveTime != null)
				return false;
		} else if (!giveTime.equals(other.giveTime))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (materialsType == null) {
			if (other.materialsType != null)
				return false;
		} else if (!materialsType.equals(other.materialsType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orgName == null) {
			if (other.orgName != null)
				return false;
		} else if (!orgName.equals(other.orgName))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (procurementMethod == null) {
			if (other.procurementMethod != null)
				return false;
		} else if (!procurementMethod.equals(other.procurementMethod))
			return false;
		if (purchaseId == null) {
			if (other.purchaseId != null)
				return false;
		} else if (!purchaseId.equals(other.purchaseId))
			return false;
		if (purchaseRequiredId == null) {
			if (other.purchaseRequiredId != null)
				return false;
		} else if (!purchaseRequiredId.equals(other.purchaseRequiredId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (taskNature == null) {
			if (other.taskNature != null)
				return false;
		} else if (!taskNature.equals(other.taskNature))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}
	
}
