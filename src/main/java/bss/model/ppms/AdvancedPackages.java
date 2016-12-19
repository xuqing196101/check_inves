/**
 * 
 */
package bss.model.ppms;

import java.util.Date;
import java.util.List;




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
	
	

  
	
}
