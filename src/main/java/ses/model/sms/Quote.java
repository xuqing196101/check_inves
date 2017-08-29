package ses.model.sms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;

/**
 * 版权：(C) 版权所有 
 * <简述>供应商报价实体类
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public class Quote {
    /**
     * 主键
     */
    private String id;

    /**
     * 创建时间
     */
    private Timestamp createdAt;
    
    /**
     * 报价
     */
    private BigDecimal quotePrice;
    
    /**
     * 包ID
     */
    private String packageId;

    /**
     * 项目ID
     */
    private String projectId;
    
    /**
     * 供应商ID
     */
    private String supplierId;
    
    /**
     * 产品ID
     */
    private String productId;
    
    /**
     * 包报价总金额
     */
    private BigDecimal total;
    
    /**
     * 成交时间
     */
    private String deliveryTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 关联项目实体
     */
    private Project project;
    
    /**
     * 关联包实体
     */
    private Packages packages;
    
    /**
     * 关联项目明细实体
     */
    private ProjectDetail projectDetail;
    
    private AdvancedDetail advancedDetail;
    
    /**
     * 关联供应商实体
     */
    private Supplier supplier;
    
    /**
     * 总金额
     */
    private BigDecimal totalMoney;
    
    /**
     * 总金额转为大写中文
     */
    private String totalMoneyNames;
    
    //是否到场
    private Integer isTurnUp;
    
    //每个包下面的供应商
    private List<Supplier> suList;
    
    //放弃报价  3是假删除
    private Integer isRemove;
    
    private String giveUpReason;
    
    private String supplierName;
    
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getIsRemove() {
        return isRemove;
    }

    public void setIsRemove(Integer isRemove) {
        this.isRemove = isRemove;
    }

    public String getGiveUpReason() {
        return giveUpReason;
    }

    public void setGiveUpReason(String giveUpReason) {
        this.giveUpReason = giveUpReason;
    }

    public List<Supplier> getSuList() {
        return suList;
    }

    public void setSuList(List<Supplier> suList) {
        this.suList = suList;
    }

    public Integer getIsTurnUp() {
        return isTurnUp;
    }

    public void setIsTurnUp(Integer isTurnUp) {
        this.isTurnUp = isTurnUp;
    }

    public String getTotalMoneyNames() {
        return totalMoneyNames;
    }

    public void setTotalMoneyNames(String totalMoneyNames) {
        this.totalMoneyNames = totalMoneyNames;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProjectDetail getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(ProjectDetail projectDetail) {
        this.projectDetail = projectDetail;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    public AdvancedDetail getAdvancedDetail() {
        return advancedDetail;
    }

    public void setAdvancedDetail(AdvancedDetail advancedDetail) {
        this.advancedDetail = advancedDetail;
    }
    
}