package bss.model.pms;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
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

    /**
     * @Fields status : 
     * 1:审核轮次设置，2：已下达， 3：第一轮审核，4：第二轮审核人员设置，5：第二轮审核
     * 6：第三轮审核人员设置，7：第三轮审核，8：审核结束，12：直接下达
     */
    private Integer status;

    private String purchaseId;

    private Date createdAt;

    private Date orderAt;

    private Date updatedAt;

    private Integer position;

    private String planNo;
    
    private String goodsType;
    
    private String purchaseType;
     
    private String taskId;
    
    private Integer totalAudit;

    private Integer auditTurn;
    
    private String userId;
    
    private String sign;
    
    public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

	public String getPlanNo() {
		return planNo;
	}

	public void setPlanNo(String planNo) {
		 this.planNo = planNo == null ? null : planNo.trim();
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType == null ? null : goodsType.trim();
	}

	public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType == null ? null : purchaseType.trim();
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId == null ? null : taskId.trim();
	}

	public Integer getTotalAudit() {
		return totalAudit;
	}

	public void setTotalAudit(Integer totalAudit) {
		this.totalAudit = totalAudit;
	}

	public Integer getAuditTurn() {
		return auditTurn;
	}

	public void setAuditTurn(Integer auditTurn) {
		this.auditTurn = auditTurn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
   
	
	
}