package bss.model.pms;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Title: PurchaseRequired
 * @Description: 采购计划明细实体 
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:19:59
 *
 */
public class PurchaseDetail {

  private String id;

  private String planName;

  private String planNo;
  
  private String referenceNo;//计划文号

  private String planType;

  private String department;//需求部门

  private String goodsName;//物资类别及名称

  private String stand;

  private String qualitStand;

  private String item;

  private BigDecimal purchaseCount;

  private BigDecimal price;

  private BigDecimal budget;//预算

  private String deliverDate;//交货期限

  private String purchaseType;//购买型号

  private String supplier;

  private String isFreeTax;

  private String goodsUse;

  private String useUnit;

  private Date createdAt;

  private String userId;

  private String parentId;

  private String reqLevel;

  private String status;

  private String memo;

  private String seq;//序号
    
  private String historyStatus;
    
  private String goodsType;
    
  private String organization;//采购机构
    
  private Date auditDate;
    
  private Integer isMaster;
    
  private Integer isDelete;
    
  private Integer isCollect;
    
  private String code;
    
  private String onePurchaseType;
    
  private String oneOrganiza;
    
  private String oneAdvice;
    
  private String twoTechAdvice;
    
  private String twoAdvice;
    
  private String threePurchaseType;
    
  private String threeOrganiza;
    
  private String threeAdvice;
    
  private String brand;
    
  private String userUnit;
    
  private Integer detailStatus;
    
  private String reason;
    
  private String recorderMobile;
    
  private Integer advancedStatus;
    
  /**
   * @Fields projectStatus : 0:未被项目立项引用  1：已被立项 2：暂被引用
   */
  private Integer projectStatus;
  
  private String uniqueId;
  
  private String fileId;

  private String sign;
  
  private String progressBar;
  
  
  
  public String getSign() {
	return sign;
}

public void setSign(String sign) {
	this.sign = sign;
}

public Integer getProjectStatus() {
    return projectStatus;
  }

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo == null ? null : planNo.trim();
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName == null ? null : goodsName.trim();
  }

    public String getStand() {
        return stand;
    }

    public void setStand(String stand) {
        this.stand = stand == null ? null : stand.trim();
    }

    public String getQualitStand() {
        return qualitStand;
    }

    public void setQualitStand(String qualitStand) {
        this.qualitStand = qualitStand == null ? null : qualitStand.trim();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item == null ? null : item.trim();
    }

    public BigDecimal getPurchaseCount() {
        return purchaseCount;
    }

    public void setPurchaseCount(BigDecimal purchaseCount) {
        this.purchaseCount = purchaseCount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public String getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(String deliverDate) {
        this.deliverDate = deliverDate == null ? null : deliverDate.trim();
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        this.purchaseType = purchaseType == null ? null : purchaseType.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getIsFreeTax() {
        return isFreeTax;
    }

    public void setIsFreeTax(String isFreeTax) {
        this.isFreeTax = isFreeTax == null ? null : isFreeTax.trim();
    }

    public String getGoodsUse() {
        return goodsUse;
    }

    public void setGoodsUse(String goodsUse) {
        this.goodsUse = goodsUse == null ? null : goodsUse.trim();
    }

    public String getUseUnit() {
        return useUnit;
    }

    public void setUseUnit(String useUnit) {
        this.useUnit = useUnit == null ? null : useUnit.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId == null ? null : parentId.trim();
    }

    public String getReqLevel() {
        return reqLevel;
    }

    public void setReqLevel(String reqLevel) {
        this.reqLevel = reqLevel == null ? null : reqLevel.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq == null ? null : seq.trim();
	}

	public String getHistoryStatus() {
		return historyStatus;
	}

	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus == null ? null : historyStatus.trim();
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType == null ? null : goodsType.trim();
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		  this.organization = organization == null ? null : organization.trim();
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(Integer isMaster) {
		this.isMaster = isMaster;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsCollect() {
		return isCollect;
	}

	public void setIsCollect(Integer isCollect) {
		this.isCollect = isCollect;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		  this.code = code == null ? null : code.trim();
	}

	public String getOnePurchaseType() {
		return onePurchaseType;
	}

	public void setOnePurchaseType(String onePurchaseType) {
		  this.onePurchaseType = onePurchaseType == null ? null : onePurchaseType.trim();
	}

	public String getOneOrganiza() {
		return oneOrganiza;
	}

	public void setOneOrganiza(String oneOrganiza) {
		  this.oneOrganiza = oneOrganiza == null ? null : oneOrganiza.trim();
	}

	public String getOneAdvice() {
		return oneAdvice;
	}

	public void setOneAdvice(String oneAdvice) {
		  this.oneAdvice = oneAdvice == null ? null : oneAdvice.trim();
	}

	public String getTwoTechAdvice() {
		return twoTechAdvice;
	}

	public void setTwoTechAdvice(String twoTechAdvice) {
		  this.twoTechAdvice = twoTechAdvice == null ? null : twoTechAdvice.trim();
	}

	public String getTwoAdvice() {
		return twoAdvice;
	}

	public void setTwoAdvice(String twoAdvice) {
		  this.twoAdvice = twoAdvice == null ? null : twoAdvice.trim();
	}

	public String getThreePurchaseType() {
		return threePurchaseType;
	}

	public void setThreePurchaseType(String threePurchaseType) {
		  this.threePurchaseType = threePurchaseType == null ? null : threePurchaseType.trim();
	}

	public String getThreeOrganiza() {
		return threeOrganiza;
	}

	public void setThreeOrganiza(String threeOrganiza) {
		  this.threeOrganiza = threeOrganiza == null ? null : threeOrganiza.trim();
	}

	public String getThreeAdvice() {
		return threeAdvice;
	}

	public void setThreeAdvice(String threeAdvice) {
		  this.threeAdvice = threeAdvice == null ? null : threeAdvice.trim();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		  this.brand = brand == null ? null : brand.trim();
	}

	public String getUserUnit() {
		return userUnit;
	}

	public void setUserUnit(String userUnit) {
		this.userUnit = userUnit;
	}

	public Integer getDetailStatus() {
		return detailStatus;
	}

	public void setDetailStatus(Integer detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		 this.reason = reason == null ? null : reason.trim();
	}

	public String getRecorderMobile() {
		return recorderMobile;
	}

	public void setRecorderMobile(String recorderMobile) {
		this.recorderMobile = recorderMobile;
	}

	public Integer getAdvancedStatus() {
		return advancedStatus;
	}

	public void setAdvancedStatus(Integer advancedStatus) {
		this.advancedStatus = advancedStatus;
	}

  public String getReferenceNo() {
    return referenceNo;
  }

  public void setReferenceNo(String referenceNo) {
    this.referenceNo = referenceNo;
  }

	public String getUniqueId() {
		return uniqueId;
	}
	
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	
	public String getFileId() {
		return fileId;
	}
	
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

    public String getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(String progressBar) {
        this.progressBar = progressBar;
    }
    
}