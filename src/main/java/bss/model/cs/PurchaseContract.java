package bss.model.cs;

import java.math.BigDecimal;

import ses.model.oms.PurchaseDep;
import ses.model.sms.Supplier;

public class PurchaseContract {
	/**
	 * @Fields id : 主键
	 */
    private String id;
    
    /**
	 * @Fields id : 编号
	 */
    private String code;
    
    /**
	 * @Fields id : 合同名称
	 */
    private String name;
    
    /**
	 * @Fields id : 成交金额
	 */
    private BigDecimal money;
    
    /**
	 * @Fields id : 项目名称
	 */
    private String projectName;
    
    /**
	 * @Fields id : 成交供应商
	 */
    private Supplier supplier;
    
    /**
	 * @Fields id : 需求部门
	 */
    private String demandSector;
    
    /**
	 * @Fields id : 计划任务文号
	 */
    private String planFileName;
    
    /**
	 * @Fields id : 预算金额
	 */
    private BigDecimal budget;
    
    /**
	 * @Fields id : 年度
	 */
    private Short year;
    
    /**
	 * @Fields id : 顶级预算科目
	 */
    private String budgetSubjectItem;
    
    /**
	 * @Fields id : 采购机构
	 */
    private PurchaseDep purchaseDep;
    
    /**
	 * @Fields id : 包号
	 */
    private Long packageNum;
    
    /**
	 * @Fields id : 合同批准文号
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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