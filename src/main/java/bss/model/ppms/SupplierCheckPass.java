package bss.model.ppms;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import bss.model.cs.PurchaseContract;
import ses.model.sms.Supplier;

public class SupplierCheckPass {

	
	
	private Project project;
	private Packages packages;
	private String purchaseDep;
	private String totalScoreString;
	private List<theSubject> subjects;
	private PurchaseContract pc;
	
	

  public PurchaseContract getPc() {
		return pc;
	}

	public void setPc(PurchaseContract pc) {
		this.pc = pc;
	}

/**
   * 是否刪除
   */
  private Integer isDeleted;

  /**
   * 金额占比
   */
  private String priceRatio;

  /**
   * 中标金额
   */
  private BigDecimal wonPrice;

  /**
   * 供应商
   */
  private Supplier supplier;

  /**
   * 选中的供应商
   */
  private List<Packages> packagesList;

  /**
   * 是否已生成合同
   */
  private Integer isCreateContract;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
   * </pre>
   */
  private String id;

  /**
   * <pre>
   * 项目id
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
   * </pre>
   */
  private String projectId;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
   * </pre>
   */
  private Date joinTime;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
   * </pre>
   */
  private String supplierId;

  /**
   * 排名
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.BID_STATUS
   * </pre>
   */
  private Integer ranking;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_PRICE
   * </pre>
   */
  private BigDecimal totalPrice;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
   * </pre>
   */
  private BigDecimal totalScore;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
   * </pre>
   */
  private String packageId;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
   * </pre>
   */
  private Short isWonBid;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
   * </pre>
   */
  private Short isSendNotice;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
   * </pre>
   */
  private Date createdAt;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
   * </pre>
   */
  private Date updatedAt;
  
  /**操作者确认中标供应商的时间点
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.CONFIRM_TIME
   * </pre>
   */
  private Date confirmTime;

  /**
   * <pre>
   * 表字段 : T_BSS_PPMS_SUPPLIER_CHECK_PASS.CONTRACT_ID
   * </pre>
   */
  private String contractId;

  
  
  private BigDecimal money;
  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID：null
   */
  public String getId() {
    return id;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID
   * </pre>
   *
   * @param id
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.ID：null
   */
  public void setId(String id) {
    this.id = id == null ? null : id.trim();
  }

  /**
   * <pre>
   * 获取：项目id
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID：项目id
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * <pre>
   * 设置：项目id
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID
   * </pre>
   *
   * @param projectId
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.PROJECT_ID：项目id
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId == null ? null : projectId.trim();
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME：null
   */
  public Date getJoinTime() {
    return joinTime;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME
   * </pre>
   *
   * @param joinTime
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.JOIN_TIME：null
   */
  public void setJoinTime(Date joinTime) {
    this.joinTime = joinTime;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID：null
   */
  public String getSupplierId() {
    return supplierId;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID
   * </pre>
   *
   * @param supplierId
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.SUPPLIER_ID：null
   */
  public void setSupplierId(String supplierId) {
    this.supplierId = supplierId == null ? null : supplierId.trim();
  }




  /**
   * @return Returns the ranking.
   */
  public Integer getRanking() {
    return ranking;
  }

  /**
   * @param ranking The ranking to set.
   */
  public void setRanking(Integer ranking) {
    this.ranking = ranking;
  }

  /**
   * @return Returns the totalPrice.
   */
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  /**
   * @param totalPrice The totalPrice to set.
   */
  public void setTotalPrice(BigDecimal totalPrice) {
    this.totalPrice = totalPrice;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE：null
   */
  public BigDecimal getTotalScore() {
    return totalScore;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE
   * </pre>
   *
   * @param zero
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.TOTAL_SCORE：null
   */
  public void setTotalScore(BigDecimal zero) {
    this.totalScore = zero;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID：null
   */
  public String getPackageId() {
    return packageId;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID
   * </pre>
   *
   * @param packageId
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.PACKAGE_ID：null
   */
  public void setPackageId(String packageId) {
    this.packageId = packageId == null ? null : packageId.trim();
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID：null
   */
  public Short getIsWonBid() {
    return isWonBid;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID
   * </pre>
   *
   * @param isWonBid
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_WON_BID：null
   */
  public void setIsWonBid(Short isWonBid) {
    this.isWonBid = isWonBid;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE：null
   */
  public Short getIsSendNotice() {
    return isSendNotice;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE
   * </pre>
   *
   * @param isSendNotice
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.IS_SEND_NOTICE：null
   */
  public void setIsSendNotice(Short isSendNotice) {
    this.isSendNotice = isSendNotice;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT：null
   */
  public Date getCreatedAt() {
    return createdAt;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT
   * </pre>
   *
   * @param createdAt
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.CREATED_AT：null
   */
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  /**
   * <pre>
   * 获取：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
   * </pre>
   *
   * @return T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT：null
   */
  public Date getUpdatedAt() {
    return updatedAt;
  }

  /**
   * <pre>
   * 设置：null
   * 表字段：T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT
   * </pre>
   *
   * @param updatedAt
   *            T_BSS_PPMS_SUPPLIER_CHECK_PASS.UPDATED_AT：null
   */
  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  /**
   * @return Returns the supplier.
   */
  public Supplier getSupplier() {
    return supplier;
  }

  /**
   * @param supplier The supplier to set.
   */
  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  /**
   * @return Returns the packagesList.
   */
  public List<Packages> getPackagesList() {
    return packagesList;
  }

  /**
   * @param packagesList The packagesList to set.
   */
  public void setPackagesList(List<Packages> packagesList) {
    this.packagesList = packagesList;
  }

  public Integer getIsCreateContract() {
    return isCreateContract;
  }

  public void setIsCreateContract(Integer isCreateContract) {
    this.isCreateContract = isCreateContract;
  }

  /**
   * @return Returns the priceRatio.
   */
  public String getPriceRatio() {
    return priceRatio;
  }

  /**
   * @param priceRatio The priceRatio to set.
   */
  public void setPriceRatio(String priceRatio) {
    this.priceRatio = priceRatio;
  }

  /**
   * @return Returns the wonPrice.
   */
  public BigDecimal getWonPrice() {
    return wonPrice;
  }

  /**
   * @param wonPrice The wonPrice to set.
   */
  public void setWonPrice(BigDecimal wonPrice) {
    this.wonPrice = wonPrice;
  }

  public String getContractId() {
    return contractId;
  }

  public void setContractId(String contractId) {
    this.contractId = contractId;
  }

  /**
   * @return Returns the isDeleted.
   */
  public Integer getIsDeleted() {
    return isDeleted;
  }

  /**
   * @param isDeleted The isDeleted to set.
   */
  public void setIsDeleted(Integer isDeleted) {
    this.isDeleted = isDeleted;
  }

public Date getConfirmTime() {
	return confirmTime;
}

public void setConfirmTime(Date confirmTime) {
	this.confirmTime = confirmTime;
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

public Packages getPackages() {
	return packages;
}

public void setPackages(Packages packages) {
	this.packages = packages;
}

public String getPurchaseDep() {
	return purchaseDep;
}

public void setPurchaseDep(String purchaseDep) {
	this.purchaseDep = purchaseDep;
}

public List<theSubject> getSubjects() {
	return subjects;
}

public void setSubjects(List<theSubject> subjects) {
	this.subjects = subjects;
}

public String getTotalScoreString() {
	return totalScoreString;
}

public void setTotalScoreString(String totalScoreString) {
	this.totalScoreString = totalScoreString;
}
  
  
}