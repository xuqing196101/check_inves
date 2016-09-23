package bss.model.cs;

import java.math.BigDecimal;

import bss.model.ppms.Project;

import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;

public class PurchaseContract {
	/**
	 * @Fields id : 主键
	 */
    private String id;
    
    /**
	 * @Fields code : 编号
	 */
    private String code;
    
    /**
	 * @Fields name : 合同名称
	 */
    private String name;
    
    /**
	 * @Fields money : 成交金额
	 */
    private BigDecimal money;
    
    /**
	 * @Fields projectName : 项目名称
	 */
    private Project project;
    
    /**
	 * @Fields supplier : 成交供应商
	 */
    private Supplier supplier;
    
    /**
	 * @Fields demandSector : 需求部门
	 */
    private String demandSector;
    
    /**
	 * @Fields planFileName : 计划任务文号
	 */
    private String planFileName;
    
    /**
	 * @Fields budget : 预算金额
	 */
    private BigDecimal budget;
    
    /**
	 * @Fields year : 年度
	 */
    private Short year;
    
    /**
	 * @Fields budgetSubjectItem : 顶级预算科目
	 */
    private String budgetSubjectItem;
    
    /**
	 * @Fields purchaseDep : 采购机构
	 */
    private PurchaseDep purchaseDep;
    
    /**
	 * @Fields packageNum : 包号
	 */
    private Long packageNum;
    
    /**
	 * @Fields approvalNumber : 合同批准文号
	 */
    private String approvalNumber;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public PurchaseDep getPurchaseDep() {
		return purchaseDep;
	}

	public void setPurchaseDep(PurchaseDep purchaseDep) {
		this.purchaseDep = purchaseDep;
	}

	public String getDemandSector() {
        return demandSector;
    }

    public void setDemandSector(String demandSector) {
        this.demandSector = demandSector == null ? null : demandSector.trim();
    }

    public String getPlanFileName() {
        return planFileName;
    }

    public void setPlanFileName(String planFileName) {
        this.planFileName = planFileName == null ? null : planFileName.trim();
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Short getYear() {
        return year;
    }

    public void setYear(Short year) {
        this.year = year;
    }

    public String getBudgetSubjectItem() {
        return budgetSubjectItem;
    }

    public void setBudgetSubjectItem(String budgetSubjectItem) {
        this.budgetSubjectItem = budgetSubjectItem == null ? null : budgetSubjectItem.trim();
    }

    public Long getPackageNum() {
        return packageNum;
    }

    public void setPackageNum(Long packageNum) {
        this.packageNum = packageNum;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber == null ? null : approvalNumber.trim();
    }
}