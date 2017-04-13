/**
 * 
 */
package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import ses.model.ems.ExpExtCondition;
import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
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

  private Supplier supplier;//用于拆分供应商展示

  private String supplierId;

  private String supplierCheckPassId;

  private String isCreateContract;

  private String contractId;

  private Negotiation negotiation;
  
  private NegotiationReport negotiationReport;

  private BigDecimal wonPrice;

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

}
