package bss.model.sstps;

import java.util.Date;

import bss.model.cs.PurchaseContract;
import ses.model.bms.User;

/**
* @Title:AppraisalContract
* @Description: 单一来源审价实体类
* @author Shen Zhenfei
* @date 2016-9-18下午4:40:42
 */
public class AppraisalContract {
	
	/**
	 * @Fields id : 主键
	 */
    private String id;
    
    /**
     * @Fields contractId : 单一采购合同
     */
    private Contracts contract;

    /**
     * @Fields purchaseContract : 采购合同
     */
    private PurchaseContract purchaseContract;

    /**
     * @Fields appraisal : 审价：0不审价，1审价中，2审价
     */
    private Integer appraisal;
    
    /**
     * @Fields distribution : 分配：0没分配，1分配
     */
    private Integer distribution;
    
    /**
     * @Fields user: 分配人
     */
    private User user;
    
    /**
     * @Fields name: 合同名称
     */
    private String name;
    
    /**
     * @Fields code: 合同编号
     */
    private String code;
    
    /**
     * @Fields money: 合同金额(万元)
     */
    private String money;
    
    /**
     * @Fields supplierName: 供应商名称
     */
    private String supplierName;
    
    /**
     * @Fields createdAt : 创建时间
     */
    private Date createdAt;

    /**
     * @Fields updatedAt : 修改时间
     */
    private Date updatedAt;
    
    /**
     * @Fields type : 合同类型：0：单一合同，1：采购合同
     */
    private Integer type;
    
    /**
     * @Fields purchaseDepName : 采购机构
     */
    private String purchaseDepName;
    
    /**
     * @Fields appraisalTask : 审价任务
     */
    private String appraisalTask;
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(Integer appraisal) {
        this.appraisal = appraisal;
    }


	public PurchaseContract getPurchaseContract() {
		return purchaseContract;
	}

	public void setPurchaseContract(PurchaseContract purchaseContract) {
		this.purchaseContract = purchaseContract;
	}

	public Integer getDistribution() {
		return distribution;
	}

	public void setDistribution(Integer distribution) {
		this.distribution = distribution;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Contracts getContract() {
		return contract;
	}

	public void setContract(Contracts contract) {
		this.contract = contract;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPurchaseDepName() {
		return purchaseDepName;
	}

	public void setPurchaseDepName(String purchaseDepName) {
		this.purchaseDepName = purchaseDepName;
	}

	public String getAppraisalTask() {
		return appraisalTask;
	}

	public void setAppraisalTask(String appraisalTask) {
		this.appraisalTask = appraisalTask;
	}
	
}