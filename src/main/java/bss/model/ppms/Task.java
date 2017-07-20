package bss.model.ppms;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;


public class Task {
    
	private String id;
	
	@NotBlank(message = "任务名称不能为空") 
    private String name; //任务名称
	
    private String purchaseId; //采购机构
	
	@NotBlank(message = "计划编号不能为空") 
    private String documentNumber;

    private Date giveTime; //下达时间
    
    private Integer status;
    
    private String procurementMethod;
    
    private String purchaseRequiredId;
    
    private Integer isDeleted;
    
    private String materialsType;
    
    private Date year;
    
    private Date acceptTime; //受领时间
    
    private String collectId;
    
    private String passWord;
    
    private Integer taskNature; //任务性质(0正常任务1预研任务)
    
    private String orgId; //采购管理部门
    
    private String orgName;
    
    private Integer notDetail;
    
    private String createrId; //下达人
    
    private String userId; //受领人

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
    

	public Integer getNotDetail() {
        return notDetail;
    }

    public void setNotDetail(Integer notDetail) {
        this.notDetail = notDetail;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
}
