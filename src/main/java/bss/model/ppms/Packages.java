/**
 * 
 */
package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.model.sms.SupplierCondition;
import ses.model.sms.SupplierExtRelate;
import bss.model.pms.CollectPlan;
import bss.model.prms.FirstAudit;



/**
 * @Title:Package
 * @Description: 分包实体类
 * @author ZhaoBo
 * @date 2016-10-9下午1:57:22
 */
public class Packages {

  
  private List<Expert> listExperts;

  private List<Supplier> listSupplier;

  private List<SupplierCheckPass> listCheckPasses;

  private List<ProjectExtract> listProjectExtract;

  private List<SupplierExtRelate> listExtRelate;

  private List<ExpExtCondition> listExpExtCondition;

  private List<SupplierCondition> listSupplierCondition;

  private List<FirstAudit> listFirstAudit;
  
  
  private Map<String, List<Quote>> quotes;//历史报价

  //经济评审
  List<MarkTerm> listScoreEconomy;

  //技术技术评审
  List<MarkTerm> listScoreTechnology;
  
  //经济基准价法+最低价法评审
  List<FirstAudit> listMinimumEconomy;
  //技术Technology
  List<FirstAudit> listMinimumTechnology;

  private String id;

  private String name;

  private Integer isDeleted;

  private Date createdAt;

  private Date updatedAt;

  private Integer status;

  private String markTermTree;
  private String bidMethodId;
  private String bidMethodName;//评分办法名称
  private String bidMethodTypeName;//评标方法
  private String bidMethodMaxScore ;//

  private String purchaseType;

  private Integer isImport;

  private List<ProjectDetail> projectDetails;

  private List<SupplierCheckPass> supplierList; 
  
  private List<CollectPlan> collectPlan;

  private Project project;

  private String supplierNames;

  private String projectId;
  
  private String projectName;

  private Supplier supplier;//用于拆分供应商展示

  private String supplierId;

  private String supplierCheckPassId;

  private String isCreateContract;

  private String contractId;

  private Negotiation negotiation;
  
  private NegotiationReport negotiationReport;

  private BigDecimal wonPrice;
  
  private String oldFlowId;//终止流程id  默认null
  

  private String newFlowId;//开始流程id  默认null

  
  private String editFlowId;//转竟谈当前流程ID 默认null




/**
   * 是否维护符合性审查项 0 未维护 1:维护中 2：已维护
   */
  private Integer isEditFirst;

  /**
   * 是否维护经济技术评分细则 0 未维护 1:维护中 2：已维护
   */
  private Integer isEditSecond;

  /**
   * 是否结束报价  0：未结束 1：已结束
   */
  private Integer isEndPrice;

  /**
   * 多次报价时间（查询报价历史的时候用得到）
   */
  private List<Date> dataList;

  /**这个包的项目预算*/
  private BigDecimal projectBudget;
  
  /**每个包下面的供应商*/
  private List<Supplier> suList;
  
  private Date techniqueTime; //评审技术性时间
  
  private Date qualificationTime; //评审资格性时间
  
  private String projectStatus; //项目状态
  
  private String packageNumber; //包号
  

  public List<Supplier> getSuList() {
      return suList;
  }

  public void setSuList(List<Supplier> suList) {
      this.suList = suList;
  }

  public BigDecimal getProjectBudget() {
    return projectBudget;
  }

  public void setProjectBudget(BigDecimal projectBudget) {
    this.projectBudget = projectBudget;
  }

  public List<Date> getDataList() {
    return dataList;
  }

  public void setDataList(List<Date> dataList) {
    this.dataList = dataList;
  }

  public BigDecimal getWonPrice() {
    return wonPrice;
  }

  public void setWonPrice(BigDecimal wonPrice) {
    this.wonPrice = wonPrice;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  public String getIsCreateContract() {
    return isCreateContract;
  }

  public void setIsCreateContract(String isCreateContract) {
    this.isCreateContract = isCreateContract;
  }

  //判断是否有评分办法 1 有 2 没有
  private Integer isHaveScoreMethod;

  public Integer getIsHaveScoreMethod() {
    return isHaveScoreMethod;
  }

  public void setIsHaveScoreMethod(Integer isHaveScoreMethod) {
    this.isHaveScoreMethod = isHaveScoreMethod;
  }

  public String getSupplierId() {
    return supplierId;
  }

  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId;
  }

  public String getSupplierCheckPassId() {
    return supplierCheckPassId;
  }

  public void setSupplierCheckPassId(String supplierCheckPassId) {
    this.supplierCheckPassId = supplierCheckPassId;
  }

  private List<SaleTender> saleTenderList;

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  public String getSupplierNames() {
    return supplierNames;
  }

  public void setSupplierNames(String supplierNames) {
    this.supplierNames = supplierNames;
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

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMarkTermTree() {
    return markTermTree;
  }

  public void setMarkTermTree(String markTermTree) {
    this.markTermTree = markTermTree;
  }

  public String getPurchaseType() {
    return purchaseType;
  }

  public void setPurchaseType(String purchaseType) {
    this.purchaseType = purchaseType;
  }

  public Integer getIsImport() {
    return isImport;
  }

  public void setIsImport(Integer isImport) {
    this.isImport = isImport;
  }

  public List<ProjectDetail> getProjectDetails() {
    return projectDetails;
  }

  public void setProjectDetails(List<ProjectDetail> projectDetails) {
    this.projectDetails = projectDetails;
  }

  /**
   * @return Returns the supplierList.
   */
  public List<SupplierCheckPass> getSupplierList() {
    return supplierList;
  }

  /**
   * @param supplierList The supplierList to set.
   */
  public void setSupplierList(List<SupplierCheckPass> supplierList) {
    this.supplierList = supplierList;
  }

  public String getBidMethodId() {
    return bidMethodId;
  }

  public void setBidMethodId(String bidMethodId) {
    this.bidMethodId = bidMethodId;
  }

  public String getBidMethodName() {
    return bidMethodName;
  }

  public void setBidMethodName(String bidMethodName) {
    this.bidMethodName = bidMethodName;
  }

  public String getBidMethodTypeName() {
    return bidMethodTypeName;
  }

  public void setBidMethodTypeName(String bidMethodTypeName) {
    this.bidMethodTypeName = bidMethodTypeName;
  }

  public String getBidMethodMaxScore() {
    return bidMethodMaxScore;
  }

  public void setBidMethodMaxScore(String bidMethodMaxScore) {
    this.bidMethodMaxScore = bidMethodMaxScore;
  }

  /**
   * @return Returns the listExperts.
   */
  public List<Expert> getListExperts() {
    return listExperts;
  }

  /**
   * @param listExperts The listExperts to set.
   */
  public void setListExperts(List<Expert> listExperts) {
    this.listExperts = listExperts;
  }

  /**
   * @return Returns the listSupplier.
   */
  public List<Supplier> getListSupplier() {
    return listSupplier;
  }

  /**
   * @param listSupplier The listSupplier to set.
   */
  public void setListSupplier(List<Supplier> listSupplier) {
    this.listSupplier = listSupplier;
  }

  /**
   * @return Returns the listCheckPasses.
   */
  public List<SupplierCheckPass> getListCheckPasses() {
    return listCheckPasses;
  }

  /**
   * @param listCheckPasses The listCheckPasses to set.
   */
  public void setListCheckPasses(List<SupplierCheckPass> listCheckPasses) {
    this.listCheckPasses = listCheckPasses;
  }



  public List<SaleTender> getSaleTenderList() {
    return saleTenderList;
  }

  public void setSaleTenderList(List<SaleTender> saleTenderList) {
    this.saleTenderList = saleTenderList;
  }

  /**
   * @return Returns the listProjectExtract.
   */
  public List<ProjectExtract> getListProjectExtract() {
    return listProjectExtract;
  }

  /**
   * @param listProjectExtract The listProjectExtract to set.
   */
  public void setListProjectExtract(List<ProjectExtract> listProjectExtract) {
    this.listProjectExtract = listProjectExtract;
  }

  public Integer getIsEditFirst() {
    return isEditFirst;
  }

  public void setIsEditFirst(Integer isEditFirst) {
    this.isEditFirst = isEditFirst;
  }

  public Integer getIsEditSecond() {
    return isEditSecond;
  }

  public void setIsEditSecond(Integer isEditSecond) {
    this.isEditSecond = isEditSecond;
  }

  /**
   * @return Returns the listExpExtCondition.
   */
  public List<ExpExtCondition> getListExpExtCondition() {
    return listExpExtCondition;
  }

  /**
   * @param listExpExtCondition The listExpExtCondition to set.
   */
  public void setListExpExtCondition(List<ExpExtCondition> listExpExtCondition) {
    this.listExpExtCondition = listExpExtCondition;
  }

  /**
   * @return Returns the listSupplierCondition.
   */
  public List<SupplierCondition> getListSupplierCondition() {
    return listSupplierCondition;
  }

  /**
   * @param listSupplierCondition The listSupplierCondition to set.
   */
  public void setListSupplierCondition(List<SupplierCondition> listSupplierCondition) {
    this.listSupplierCondition = listSupplierCondition;
  }

  public Integer getIsEndPrice() {
    return isEndPrice;
  }

  public void setIsEndPrice(Integer isEndPrice) {
    this.isEndPrice = isEndPrice;
  }

  /**
   * @return Returns the listFirstAudit.
   */
  public List<FirstAudit> getListFirstAudit() {
    return listFirstAudit;
  }

  /**
   * @param listFirstAudit The listFirstAudit to set.
   */
  public void setListFirstAudit(List<FirstAudit> listFirstAudit) {
    this.listFirstAudit = listFirstAudit;
  }

  /**
   * @return Returns the listScoreEconomy.
   */
  public List<MarkTerm> getListScoreEconomy() {
    return listScoreEconomy;
  }

  /**
   * @param listScoreEconomy The listScoreEconomy to set.
   */
  public void setListScoreEconomy(List<MarkTerm> listScoreEconomy) {
    this.listScoreEconomy = listScoreEconomy;
  }

  /**
   * @return Returns the listScoreTechnology.
   */
  public List<MarkTerm> getListScoreTechnology() {
    return listScoreTechnology;
  }

  /**
   * @param listScoreTechnology The listScoreTechnology to set.
   */
  public void setListScoreTechnology(List<MarkTerm> listScoreTechnology) {
    this.listScoreTechnology = listScoreTechnology;
  }

  public Negotiation getNegotiation() {
    return negotiation;
  }

  public void setNegotiation(Negotiation negotiation) {
    this.negotiation = negotiation;
  }

  
  public NegotiationReport getNegotiationReport() {
    return negotiationReport;
}

public void setNegotiationReport(NegotiationReport negotiationReport) {
    this.negotiationReport = negotiationReport;
}

/**
   * @return Returns the listExtRelate.
   */
  public List<SupplierExtRelate> getListExtRelate() {
    return listExtRelate;
  }

  /**
   * @param listExtRelate The listExtRelate to set.
   */
  public void setListExtRelate(List<SupplierExtRelate> listExtRelate) {
    this.listExtRelate = listExtRelate;
  }

  /**
   * @return Returns the listMinimumEconomy.
   */
  public List<FirstAudit> getListMinimumEconomy() {
    return listMinimumEconomy;
  }

  /**
   * @param listMinimumEconomy The listMinimumEconomy to set.
   */
  public void setListMinimumEconomy(List<FirstAudit> listMinimumEconomy) {
    this.listMinimumEconomy = listMinimumEconomy;
  }

  /**
   * @return Returns the listMinimumTechnology.
   */
  public List<FirstAudit> getListMinimumTechnology() {
    return listMinimumTechnology;
  }

  /**
   * @param listMinimumTechnology The listMinimumTechnology to set.
   */
  public void setListMinimumTechnology(List<FirstAudit> listMinimumTechnology) {
    this.listMinimumTechnology = listMinimumTechnology;
  }

    public List<CollectPlan> getCollectPlan() {
        return collectPlan;
    }
    
    public void setCollectPlan(List<CollectPlan> collectPlan) {
        this.collectPlan = collectPlan;
    }

    public Date getTechniqueTime() {
        return techniqueTime;
    }

    public void setTechniqueTime(Date techniqueTime) {
        this.techniqueTime = techniqueTime;
    }

    public Date getQualificationTime() {
        return qualificationTime;
    }

    public void setQualificationTime(Date qualificationTime) {
        this.qualificationTime = qualificationTime;
    }

	public String getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getOldFlowId() {
		return oldFlowId;
	}

	public void setOldFlowId(String oldFlowId) {
		this.oldFlowId = oldFlowId;
	}

	public String getNewFlowId() {
		return newFlowId;
	}

	public void setNewFlowId(String newFlowId) {
		this.newFlowId = newFlowId;
	}
	public String getEditFlowId() {
		return editFlowId;
	}

	public void setEditFlowId(String editFlowId) {
		this.editFlowId = editFlowId;
	}

    public String getProjectName() {
      return projectName;
    }

    public void setProjectName(String projectName) {
      this.projectName = projectName;
    }

	public String getPackageNumber() {
		return packageNumber;
	}

	public void setPackageNumber(String packageNumber) {
		this.packageNumber = packageNumber;
	}

  public Map<String, List<Quote>> getQuotes() {
    return quotes;
  }

  public void setQuotes(Map<String, List<Quote>> quotes) {
    this.quotes = quotes;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((bidMethodId == null) ? 0 : bidMethodId.hashCode());
	result = prime * result
			+ ((bidMethodMaxScore == null) ? 0 : bidMethodMaxScore.hashCode());
	result = prime * result
			+ ((bidMethodName == null) ? 0 : bidMethodName.hashCode());
	result = prime * result
			+ ((bidMethodTypeName == null) ? 0 : bidMethodTypeName.hashCode());
	result = prime * result
			+ ((collectPlan == null) ? 0 : collectPlan.hashCode());
	result = prime * result
			+ ((contractId == null) ? 0 : contractId.hashCode());
	result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
	result = prime * result + ((dataList == null) ? 0 : dataList.hashCode());
	result = prime * result
			+ ((editFlowId == null) ? 0 : editFlowId.hashCode());
	result = prime * result + ((id == null) ? 0 : id.hashCode());
	result = prime * result
			+ ((isCreateContract == null) ? 0 : isCreateContract.hashCode());
	result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
	result = prime * result
			+ ((isEditFirst == null) ? 0 : isEditFirst.hashCode());
	result = prime * result
			+ ((isEditSecond == null) ? 0 : isEditSecond.hashCode());
	result = prime * result
			+ ((isEndPrice == null) ? 0 : isEndPrice.hashCode());
	result = prime * result
			+ ((isHaveScoreMethod == null) ? 0 : isHaveScoreMethod.hashCode());
	result = prime * result + ((isImport == null) ? 0 : isImport.hashCode());
	result = prime * result
			+ ((listCheckPasses == null) ? 0 : listCheckPasses.hashCode());
	result = prime
			* result
			+ ((listExpExtCondition == null) ? 0 : listExpExtCondition
					.hashCode());
	result = prime * result
			+ ((listExperts == null) ? 0 : listExperts.hashCode());
	result = prime * result
			+ ((listExtRelate == null) ? 0 : listExtRelate.hashCode());
	result = prime * result
			+ ((listFirstAudit == null) ? 0 : listFirstAudit.hashCode());
	result = prime
			* result
			+ ((listMinimumEconomy == null) ? 0 : listMinimumEconomy.hashCode());
	result = prime
			* result
			+ ((listMinimumTechnology == null) ? 0 : listMinimumTechnology
					.hashCode());
	result = prime
			* result
			+ ((listProjectExtract == null) ? 0 : listProjectExtract.hashCode());
	result = prime * result
			+ ((listScoreEconomy == null) ? 0 : listScoreEconomy.hashCode());
	result = prime
			* result
			+ ((listScoreTechnology == null) ? 0 : listScoreTechnology
					.hashCode());
	result = prime * result
			+ ((listSupplier == null) ? 0 : listSupplier.hashCode());
	result = prime
			* result
			+ ((listSupplierCondition == null) ? 0 : listSupplierCondition
					.hashCode());
	result = prime * result
			+ ((markTermTree == null) ? 0 : markTermTree.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result
			+ ((negotiation == null) ? 0 : negotiation.hashCode());
	result = prime * result
			+ ((negotiationReport == null) ? 0 : negotiationReport.hashCode());
	result = prime * result + ((newFlowId == null) ? 0 : newFlowId.hashCode());
	result = prime * result + ((oldFlowId == null) ? 0 : oldFlowId.hashCode());
	result = prime * result
			+ ((packageNumber == null) ? 0 : packageNumber.hashCode());
	result = prime * result + ((project == null) ? 0 : project.hashCode());
	result = prime * result
			+ ((projectBudget == null) ? 0 : projectBudget.hashCode());
	result = prime * result
			+ ((projectDetails == null) ? 0 : projectDetails.hashCode());
	result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
	result = prime * result
			+ ((projectName == null) ? 0 : projectName.hashCode());
	result = prime * result
			+ ((projectStatus == null) ? 0 : projectStatus.hashCode());
	result = prime * result
			+ ((purchaseType == null) ? 0 : purchaseType.hashCode());
	result = prime * result
			+ ((qualificationTime == null) ? 0 : qualificationTime.hashCode());
	result = prime * result + ((quotes == null) ? 0 : quotes.hashCode());
	result = prime * result
			+ ((saleTenderList == null) ? 0 : saleTenderList.hashCode());
	result = prime * result + ((status == null) ? 0 : status.hashCode());
	result = prime * result + ((suList == null) ? 0 : suList.hashCode());
	result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
	result = prime
			* result
			+ ((supplierCheckPassId == null) ? 0 : supplierCheckPassId
					.hashCode());
	result = prime * result
			+ ((supplierId == null) ? 0 : supplierId.hashCode());
	result = prime * result
			+ ((supplierList == null) ? 0 : supplierList.hashCode());
	result = prime * result
			+ ((supplierNames == null) ? 0 : supplierNames.hashCode());
	result = prime * result
			+ ((techniqueTime == null) ? 0 : techniqueTime.hashCode());
	result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
	result = prime * result + ((wonPrice == null) ? 0 : wonPrice.hashCode());
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
	Packages other = (Packages) obj;
	if (bidMethodId == null) {
		if (other.bidMethodId != null)
			return false;
	} else if (!bidMethodId.equals(other.bidMethodId))
		return false;
	if (bidMethodMaxScore == null) {
		if (other.bidMethodMaxScore != null)
			return false;
	} else if (!bidMethodMaxScore.equals(other.bidMethodMaxScore))
		return false;
	if (bidMethodName == null) {
		if (other.bidMethodName != null)
			return false;
	} else if (!bidMethodName.equals(other.bidMethodName))
		return false;
	if (bidMethodTypeName == null) {
		if (other.bidMethodTypeName != null)
			return false;
	} else if (!bidMethodTypeName.equals(other.bidMethodTypeName))
		return false;
	if (collectPlan == null) {
		if (other.collectPlan != null)
			return false;
	} else if (!collectPlan.equals(other.collectPlan))
		return false;
	if (contractId == null) {
		if (other.contractId != null)
			return false;
	} else if (!contractId.equals(other.contractId))
		return false;
	if (createdAt == null) {
		if (other.createdAt != null)
			return false;
	} else if (!createdAt.equals(other.createdAt))
		return false;
	if (dataList == null) {
		if (other.dataList != null)
			return false;
	} else if (!dataList.equals(other.dataList))
		return false;
	if (editFlowId == null) {
		if (other.editFlowId != null)
			return false;
	} else if (!editFlowId.equals(other.editFlowId))
		return false;
	if (id == null) {
		if (other.id != null)
			return false;
	} else if (!id.equals(other.id))
		return false;
	if (isCreateContract == null) {
		if (other.isCreateContract != null)
			return false;
	} else if (!isCreateContract.equals(other.isCreateContract))
		return false;
	if (isDeleted == null) {
		if (other.isDeleted != null)
			return false;
	} else if (!isDeleted.equals(other.isDeleted))
		return false;
	if (isEditFirst == null) {
		if (other.isEditFirst != null)
			return false;
	} else if (!isEditFirst.equals(other.isEditFirst))
		return false;
	if (isEditSecond == null) {
		if (other.isEditSecond != null)
			return false;
	} else if (!isEditSecond.equals(other.isEditSecond))
		return false;
	if (isEndPrice == null) {
		if (other.isEndPrice != null)
			return false;
	} else if (!isEndPrice.equals(other.isEndPrice))
		return false;
	if (isHaveScoreMethod == null) {
		if (other.isHaveScoreMethod != null)
			return false;
	} else if (!isHaveScoreMethod.equals(other.isHaveScoreMethod))
		return false;
	if (isImport == null) {
		if (other.isImport != null)
			return false;
	} else if (!isImport.equals(other.isImport))
		return false;
	if (listCheckPasses == null) {
		if (other.listCheckPasses != null)
			return false;
	} else if (!listCheckPasses.equals(other.listCheckPasses))
		return false;
	if (listExpExtCondition == null) {
		if (other.listExpExtCondition != null)
			return false;
	} else if (!listExpExtCondition.equals(other.listExpExtCondition))
		return false;
	if (listExperts == null) {
		if (other.listExperts != null)
			return false;
	} else if (!listExperts.equals(other.listExperts))
		return false;
	if (listExtRelate == null) {
		if (other.listExtRelate != null)
			return false;
	} else if (!listExtRelate.equals(other.listExtRelate))
		return false;
	if (listFirstAudit == null) {
		if (other.listFirstAudit != null)
			return false;
	} else if (!listFirstAudit.equals(other.listFirstAudit))
		return false;
	if (listMinimumEconomy == null) {
		if (other.listMinimumEconomy != null)
			return false;
	} else if (!listMinimumEconomy.equals(other.listMinimumEconomy))
		return false;
	if (listMinimumTechnology == null) {
		if (other.listMinimumTechnology != null)
			return false;
	} else if (!listMinimumTechnology.equals(other.listMinimumTechnology))
		return false;
	if (listProjectExtract == null) {
		if (other.listProjectExtract != null)
			return false;
	} else if (!listProjectExtract.equals(other.listProjectExtract))
		return false;
	if (listScoreEconomy == null) {
		if (other.listScoreEconomy != null)
			return false;
	} else if (!listScoreEconomy.equals(other.listScoreEconomy))
		return false;
	if (listScoreTechnology == null) {
		if (other.listScoreTechnology != null)
			return false;
	} else if (!listScoreTechnology.equals(other.listScoreTechnology))
		return false;
	if (listSupplier == null) {
		if (other.listSupplier != null)
			return false;
	} else if (!listSupplier.equals(other.listSupplier))
		return false;
	if (listSupplierCondition == null) {
		if (other.listSupplierCondition != null)
			return false;
	} else if (!listSupplierCondition.equals(other.listSupplierCondition))
		return false;
	if (markTermTree == null) {
		if (other.markTermTree != null)
			return false;
	} else if (!markTermTree.equals(other.markTermTree))
		return false;
	if (name == null) {
		if (other.name != null)
			return false;
	} else if (!name.equals(other.name))
		return false;
	if (negotiation == null) {
		if (other.negotiation != null)
			return false;
	} else if (!negotiation.equals(other.negotiation))
		return false;
	if (negotiationReport == null) {
		if (other.negotiationReport != null)
			return false;
	} else if (!negotiationReport.equals(other.negotiationReport))
		return false;
	if (newFlowId == null) {
		if (other.newFlowId != null)
			return false;
	} else if (!newFlowId.equals(other.newFlowId))
		return false;
	if (oldFlowId == null) {
		if (other.oldFlowId != null)
			return false;
	} else if (!oldFlowId.equals(other.oldFlowId))
		return false;
	if (packageNumber == null) {
		if (other.packageNumber != null)
			return false;
	} else if (!packageNumber.equals(other.packageNumber))
		return false;
	if (project == null) {
		if (other.project != null)
			return false;
	} else if (!project.equals(other.project))
		return false;
	if (projectBudget == null) {
		if (other.projectBudget != null)
			return false;
	} else if (!projectBudget.equals(other.projectBudget))
		return false;
	if (projectDetails == null) {
		if (other.projectDetails != null)
			return false;
	} else if (!projectDetails.equals(other.projectDetails))
		return false;
	if (projectId == null) {
		if (other.projectId != null)
			return false;
	} else if (!projectId.equals(other.projectId))
		return false;
	if (projectName == null) {
		if (other.projectName != null)
			return false;
	} else if (!projectName.equals(other.projectName))
		return false;
	if (projectStatus == null) {
		if (other.projectStatus != null)
			return false;
	} else if (!projectStatus.equals(other.projectStatus))
		return false;
	if (purchaseType == null) {
		if (other.purchaseType != null)
			return false;
	} else if (!purchaseType.equals(other.purchaseType))
		return false;
	if (qualificationTime == null) {
		if (other.qualificationTime != null)
			return false;
	} else if (!qualificationTime.equals(other.qualificationTime))
		return false;
	if (quotes == null) {
		if (other.quotes != null)
			return false;
	} else if (!quotes.equals(other.quotes))
		return false;
	if (saleTenderList == null) {
		if (other.saleTenderList != null)
			return false;
	} else if (!saleTenderList.equals(other.saleTenderList))
		return false;
	if (status == null) {
		if (other.status != null)
			return false;
	} else if (!status.equals(other.status))
		return false;
	if (suList == null) {
		if (other.suList != null)
			return false;
	} else if (!suList.equals(other.suList))
		return false;
	if (supplier == null) {
		if (other.supplier != null)
			return false;
	} else if (!supplier.equals(other.supplier))
		return false;
	if (supplierCheckPassId == null) {
		if (other.supplierCheckPassId != null)
			return false;
	} else if (!supplierCheckPassId.equals(other.supplierCheckPassId))
		return false;
	if (supplierId == null) {
		if (other.supplierId != null)
			return false;
	} else if (!supplierId.equals(other.supplierId))
		return false;
	if (supplierList == null) {
		if (other.supplierList != null)
			return false;
	} else if (!supplierList.equals(other.supplierList))
		return false;
	if (supplierNames == null) {
		if (other.supplierNames != null)
			return false;
	} else if (!supplierNames.equals(other.supplierNames))
		return false;
	if (techniqueTime == null) {
		if (other.techniqueTime != null)
			return false;
	} else if (!techniqueTime.equals(other.techniqueTime))
		return false;
	if (updatedAt == null) {
		if (other.updatedAt != null)
			return false;
	} else if (!updatedAt.equals(other.updatedAt))
		return false;
	if (wonPrice == null) {
		if (other.wonPrice != null)
			return false;
	} else if (!wonPrice.equals(other.wonPrice))
		return false;
	return true;
}

  
	
}
