package bss.model.cs;

import java.math.BigDecimal;

public class Contract {
    private String id;

    private String code;

    private String name;

    private BigDecimal money;

    private String projectName;

    private String supplierName;

    private String purchaseOrganization;

    private String demandSector;

    private String planFileName;

    private BigDecimal budget;

    private Short year;

    private String budgetSubjectItem;

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

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getPurchaseOrganization() {
        return purchaseOrganization;
    }

    public void setPurchaseOrganization(String purchaseOrganization) {
        this.purchaseOrganization = purchaseOrganization == null ? null : purchaseOrganization.trim();
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
}