/**
 * 
 */
package bss.model.ppms;

import java.util.Date;
import java.util.List;

import ses.model.ems.Expert;
import ses.model.ems.ProjectExtract;
import ses.model.sms.Supplier;



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
	
	private Project project;

	private String supplierNames;
	
	private String projectId;
	
	private Supplier supplier;//用于拆分供应商展示

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



  
	
}
