package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bss.formbean.Jzjf;

import ses.model.bms.User;
import ses.model.sms.Supplier;

public class SaleTender {
	
	
	
    public SaleTender() {
		super();
	}

    
	public SaleTender(String projectId) {
		super();
		this.projectId = projectId;
	}


	public SaleTender(String projectId, Short statusBid, String supplierId,
			Short statusBond, String userId,String packages) {
		super();
		this.projectId = projectId;
		this.statusBid = statusBid;
		this.supplierId = supplierId;
		this.statusBond = statusBond;
		this.userId = userId;
		this.packages=packages;
	}
	

	public SaleTender(String projectId, String supplierId) {
        super();
        this.projectId = projectId;
        this.supplierId = supplierId;
    }

    private Integer ranking;
    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     */
    private String id;
    
    /** 该包是否已结束 **/
    private Integer isFinish;

    /**
     * <pre>
     * 项目id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     */
    private String projectId;

    /**
     * <pre>
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     */
    private Short statusBid;

    /**
     * <pre>
     * 供应商id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     */
    private String supplierId;
    
    private String supplierName;
    
    private String armyBuinessTelephone;
    
    private String armyBuinessName;

    /**
     * <pre>
     * 保证金状态 1缴纳 2，缴纳
     * 表字段 : T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     */
    private Short statusBond;

    /**
     * <pre>
     * 创建时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     */
    private Date createdAt;

    /**
     * <pre>
     * 修改时间
     * 表字段 : T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     */
    private Date updatedAt;

    /**
     * <pre>
     * 发售人id
     * 表字段 : T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     */
    private String userId;
    
    /**
     * <pre>
     *  默认0：开标一览表   1：价格构成表 2：货物材料、部件、工具价格明细表  3：编制标书  4：绑定指标    5：完成
     * 表字段 : T_BSS_PPMS_SALE_TENDER.BID_FINISH
     * </pre>
     */
    private Short bidFinish;
    
    
    private Supplier suppliers;
    
    
    private User user;
    
    private Project project;
    
    private String packages;
    
    private List<Money> money;
    
    /**
     * 符合性审查是否通过 0：未通过 1：通过
     */
    private Integer isFirstPass;
    
    /**
     * 经济得分
     */
    private BigDecimal economicScore;
    
    /**
     * 技术得分
     */
    private BigDecimal technologyScore;
    
    /**
     * 供应商报价总金额
     */
    private BigDecimal totalPrice;
    
    /**
     * 评审结果
     */
    private String reviewResult;
    
    /**
     * 是否移除 1:被移除 0：未移除
     */
    private String isRemoved;
    
    /**
     * 移除理由
     */
    private String removedReason;
    
    /**
     * 包名字符串
     */
    private String packageNames;
    
    //上传文件groups
    private String groupsUpload;
    private String groupShow;
    private String groupsUploadId;
    private String groupShowId;
    //报价金额
    private BigDecimal total;
    //交货时间
    private String deliveryTime;
    //是否到场  0:到场 1：未到场
    private Integer isTurnUp;
    //报价表ID 判断是新增还是修改
    private String quoteId;
    //项目当前包的预算
    private BigDecimal projectBudget;
    //判断文件是否可以修改 null可以修改 不为空就不能修改
    private Integer judgeBidFile;
    //显示文件名称
    private String bidFileName;
    //显示文件名称
    private String bidFileId;
    //基准价法展示字段
    private Jzjf jzjf;
    //项目详细
    private List<ProjectDetail> projectDetail;
    /**
     * 多次报价时间（查询报价历史的时候用得到）
     */
    private List<Date> dataList;
    
    private Integer isEndPrice;
    
    private BigDecimal priceScore;//价格评分
    
    public Integer getIsEndPrice() {
        return isEndPrice;
    }


    public List<ProjectDetail> getProjectDetail() {
      return projectDetail;
    }


    public void setProjectDetail(List<ProjectDetail> projectDetail) {
      this.projectDetail = projectDetail;
    }


    public void setIsEndPrice(Integer isEndPrice) {
        this.isEndPrice = isEndPrice;
    }


    public List<Date> getDataList() {
        return dataList;
    }

    public void setDataList(List<Date> dataList) {
        this.dataList = dataList;
    }
    
    public String getBidFileId() {
        return bidFileId;
    }


    public void setBidFileId(String bidFileId) {
        this.bidFileId = bidFileId;
    }


    public String getBidFileName() {
        return bidFileName;
    }


    public void setBidFileName(String bidFileName) {
        this.bidFileName = bidFileName;
    }


    public Integer getJudgeBidFile() {
        return judgeBidFile;
    }


    public void setJudgeBidFile(Integer judgeBidFile) {
        this.judgeBidFile = judgeBidFile;
    }


    public BigDecimal getProjectBudget() {
        return projectBudget;
    }


    public void setProjectBudget(BigDecimal projectBudget) {
        this.projectBudget = projectBudget;
    }


    public String getQuoteId() {
        return quoteId;
    }


    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    

    public String getGroupsUploadId() {
        return groupsUploadId;
    }


    public void setGroupsUploadId(String groupsUploadId) {
        this.groupsUploadId = groupsUploadId;
    }


    public String getGroupShowId() {
        return groupShowId;
    }


    public void setGroupShowId(String groupShowId) {
        this.groupShowId = groupShowId;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }


    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }


    public Integer getIsTurnUp() {
        return isTurnUp;
    }


    public void setIsTurnUp(Integer isTurnUp) {
        this.isTurnUp = isTurnUp;
    }


    public String getGroupsUpload() {
        return groupsUpload;
    }


    public void setGroupsUpload(String groupsUpload) {
        this.groupsUpload = groupsUpload;
    }


    public String getGroupShow() {
        return groupShow;
    }


    public void setGroupShow(String groupShow) {
        this.groupShow = groupShow;
    }

    public String getPackageNames() {
        return packageNames;
    }


    public void setPackageNames(String packageNames) {
        this.packageNames = packageNames;
    }


    public List<Money> getMoney() {
        return money;
    }


    public void setMoney(List<Money> money) {
        this.money = money;
    }


    public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	/**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public String getId() {
        return id;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.ID
     * </pre>
     *
     * @param id
     *            T_BSS_PPMS_SALE_TENDER.ID：null
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * <pre>
     * 获取：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * <pre>
     * 设置：项目id
     * 表字段：T_BSS_PPMS_SALE_TENDER.PROJECT_ID
     * </pre>
     *
     * @param projectId
     *            T_BSS_PPMS_SALE_TENDER.PROJECT_ID：项目id
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    /**
     * <pre>
     * 获取：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public Short getStatusBid() {
        return statusBid;
    }

    /**
     * <pre>
     * 设置：null
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BID
     * </pre>
     *
     * @param statusBid
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BID：null
     */
    public void setStatusBid(Short statusBid) {
        this.statusBid = statusBid;
    }

    /**
     * <pre>
     * 获取：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public String getSupplierId() {
        return supplierId;
    }

    /**
     * <pre>
     * 设置：供应商id
     * 表字段：T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID
     * </pre>
     *
     * @param supplierId
     *            T_BSS_PPMS_SALE_TENDER.SUPPLIER_ID：供应商id
     */
    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId == null ? null : supplierId.trim();
    }

    /**
     * <pre>
     * 获取：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public Short getStatusBond() {
        return statusBond;
    }

    /**
     * <pre>
     * 设置：保证金状态 1缴纳 2，缴纳
     * 表字段：T_BSS_PPMS_SALE_TENDER.STATUS_BOND
     * </pre>
     *
     * @param statusBond
     *            T_BSS_PPMS_SALE_TENDER.STATUS_BOND：保证金状态 1缴纳 2，缴纳
     */
    public void setStatusBond(Short statusBond) {
        this.statusBond = statusBond;
    }

    /**
     * <pre>
     * 获取：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * <pre>
     * 设置：创建时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.CREATED_AT
     * </pre>
     *
     * @param createdAt
     *            T_BSS_PPMS_SALE_TENDER.CREATED_AT：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * <pre>
     * 获取：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * <pre>
     * 设置：修改时间
     * 表字段：T_BSS_PPMS_SALE_TENDER.UPDATED_AT
     * </pre>
     *
     * @param updatedAt
     *            T_BSS_PPMS_SALE_TENDER.UPDATED_AT：修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * <pre>
     * 获取：发售人id
     * 表字段：T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     *
     * @return T_BSS_PPMS_SALE_TENDER.USER_ID：发售人id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * <pre>
     * 设置：发售人id
     * 表字段：T_BSS_PPMS_SALE_TENDER.USER_ID
     * </pre>
     *
     * @param userId
     *            T_BSS_PPMS_SALE_TENDER.USER_ID：发售人id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }


	/**
	 * @return the suppliers
	 */
	public Supplier getSuppliers() {
		return suppliers;
	}


	/**
	 * @param suppliers the suppliers to set
	 */
	public void setSuppliers(Supplier suppliers) {
		this.suppliers = suppliers;
	}


	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}


	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}


    /**
     * @return Returns the bidFinish.
     */
    public Short getBidFinish() {
        return bidFinish;
    }


    /**
     * @param bidFinish The bidFinish to set.
     */
    public void setBidFinish(Short bidFinish) {
        this.bidFinish = bidFinish;
    }


    public String getPackages() {
        return packages;
    }


    public void setPackages(String packages) {
        this.packages = packages;
    }


    public Integer getIsFirstPass() {
      return isFirstPass;
    }


    public void setIsFirstPass(Integer isFirstPass) {
      this.isFirstPass = isFirstPass;
    }


    public BigDecimal getEconomicScore() {
      return economicScore;
    }


    public void setEconomicScore(BigDecimal economicScore) {
      this.economicScore = economicScore;
    }


    public BigDecimal getTechnologyScore() {
      return technologyScore;
    }


    public void setTechnologyScore(BigDecimal technologyScore) {
      this.technologyScore = technologyScore;
    }


    public String getReviewResult() {
      return reviewResult;
    }


    public void setReviewResult(String reviewResult) {
      this.reviewResult = reviewResult;
    }


    public String getIsRemoved() {
        return isRemoved;
    }


    public void setIsRemoved(String isRemoved) {
        this.isRemoved = isRemoved;
    }


    public String getRemovedReason() {
        return removedReason;
    }


    public void setRemovedReason(String removedReason) {
        this.removedReason = removedReason;
    }


    public Integer getIsFinish() {
        return isFinish;
    }


    public void setIsFinish(Integer isFinish) {
        this.isFinish = isFinish;
    }


    public BigDecimal getTotalPrice() {
      return totalPrice;
    }


    public void setTotalPrice(BigDecimal totalPrice) {
      this.totalPrice = totalPrice;
    }


    public Jzjf getJzjf() {
      return jzjf;
    }


    public void setJzjf(Jzjf jzjf) {
      this.jzjf = jzjf;
    }


    public String getSupplierName() {
        return supplierName;
    }


    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getArmyBuinessTelephone() {
        return armyBuinessTelephone;
    }


    public void setArmyBuinessTelephone(String armyBuinessTelephone) {
        this.armyBuinessTelephone = armyBuinessTelephone;
    }


    public String getArmyBuinessName() {
        return armyBuinessName;
    }


    public void setArmyBuinessName(String armyBuinessName) {
        this.armyBuinessName = armyBuinessName;
    }


    public BigDecimal getPriceScore() {
      return priceScore;
    }


    public Integer getRanking() {
      return ranking;
    }


    public void setRanking(Integer ranking) {
      this.ranking = ranking;
    }


    public void setPriceScore(BigDecimal priceScore) {
      this.priceScore = priceScore;
    }
    
}