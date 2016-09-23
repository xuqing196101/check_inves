package bss.model.pms;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Title: CollectPlan
 * @Description: 采购计划实体
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:19:49
 *
 */
public class CollectPlan {
    private String id;

    private String fileName;

    private String password;

    private String department;

    private BigDecimal budget;

    private Integer status;

    private String purchaseId;

    private Date createdAt;

    private Date orderAt;

    private Date updatedAt;

    private Long position;

    private String planNo;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId == null ? null : purchaseId.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Date orderAt) {
        this.orderAt = orderAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		 this.planNo = planNo == null ? null : planNo.trim();
	}
    
}