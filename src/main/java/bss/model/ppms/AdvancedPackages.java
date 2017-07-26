/**
 * 
 */
package bss.model.ppms;

import java.util.Date;
import java.util.List;

import ses.model.ems.ProjectExtract;

import bss.model.prms.FirstAudit;




/**
 * @Title:Package
 * @Description: 预研分包实体类
 * @author ZhaoBo
 * @date 2016-10-9下午1:57:22
 */
public class AdvancedPackages {
    
	private String id;
	
	private String name;
	
	private Integer isDeleted;
	
	private Date createdAt;
	
	private Date updatedAt;
	
	private Integer status;
	
	private String bidMethodId;
	
	private String purchaseType;
	
	private Integer isImport;
	
	private List<AdvancedDetail> advancedDetails;
	
	private AdvancedProject project;
	
	private String projectId;
	
	private String projectName;
	
	//判断是否有评分办法 1 有 2 没有
    private Integer isHaveScoreMethod;
    
    private String markTermTree;
    private String bidMethodName;//评分办法名称
    private String bidMethodTypeName;//评标方法
    private String bidMethodMaxScore ;//
    
    private List<FirstAudit> listFirstAudit;
    
    //经济基准价法+最低价法评审
    private List<FirstAudit> listMinimumEconomy;
    
    //技术Technology
    private List<FirstAudit> listMinimumTechnology;
    
    //经济评审
    private List<MarkTerm> listScoreEconomy;
    
    //技术技术评审
    private List<MarkTerm> listScoreTechnology;
    
    /**
     * 是否维护符合性审查项 0 未维护 1:维护中 2：已维护
     */
    private Integer isEditFirst;
    
    /**
     * 是否维护经济技术评分细则 0 未维护 1:维护中 2：已维护
     */
    private Integer isEditSecond;
    
    private List<SaleTender> saleTenderList;
    
    private List<ProjectExtract> listProjectExtract;

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

    public String getBidMethodId() {
        return bidMethodId;
    }

    public void setBidMethodId(String bidMethodId) {
        this.bidMethodId = bidMethodId;
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

    public List<AdvancedDetail> getAdvancedDetails() {
        return advancedDetails;
    }

    public void setAdvancedDetails(List<AdvancedDetail> advancedDetails) {
        this.advancedDetails = advancedDetails;
    }

    public AdvancedProject getProject() {
        return project;
    }

    public void setProject(AdvancedProject project) {
        this.project = project;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    public Integer getIsHaveScoreMethod() {
        return isHaveScoreMethod;
    }

    public void setIsHaveScoreMethod(Integer isHaveScoreMethod) {
        this.isHaveScoreMethod = isHaveScoreMethod;
    }

    public String getMarkTermTree() {
        return markTermTree;
    }

    public void setMarkTermTree(String markTermTree) {
        this.markTermTree = markTermTree;
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

    public List<FirstAudit> getListFirstAudit() {
        return listFirstAudit;
    }

    public void setListFirstAudit(List<FirstAudit> listFirstAudit) {
        this.listFirstAudit = listFirstAudit;
    }

    public List<FirstAudit> getListMinimumEconomy() {
        return listMinimumEconomy;
    }

    public void setListMinimumEconomy(List<FirstAudit> listMinimumEconomy) {
        this.listMinimumEconomy = listMinimumEconomy;
    }

    public List<FirstAudit> getListMinimumTechnology() {
        return listMinimumTechnology;
    }

    public void setListMinimumTechnology(List<FirstAudit> listMinimumTechnology) {
        this.listMinimumTechnology = listMinimumTechnology;
    }

    public List<MarkTerm> getListScoreEconomy() {
        return listScoreEconomy;
    }

    public void setListScoreEconomy(List<MarkTerm> listScoreEconomy) {
        this.listScoreEconomy = listScoreEconomy;
    }

    public List<MarkTerm> getListScoreTechnology() {
        return listScoreTechnology;
    }

    public void setListScoreTechnology(List<MarkTerm> listScoreTechnology) {
        this.listScoreTechnology = listScoreTechnology;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<SaleTender> getSaleTenderList() {
        return saleTenderList;
    }

    public void setSaleTenderList(List<SaleTender> saleTenderList) {
        this.saleTenderList = saleTenderList;
    }

    public List<ProjectExtract> getListProjectExtract() {
        return listProjectExtract;
    }

    public void setListProjectExtract(List<ProjectExtract> listProjectExtract) {
        this.listProjectExtract = listProjectExtract;
    }
    
}
