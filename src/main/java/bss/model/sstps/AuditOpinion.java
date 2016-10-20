package bss.model.sstps;

import java.util.Date;

/**
* @Title:AuditOpinion 
* @Description: 审核意见栏
* @author Shen Zhenfei
* @date 2016-10-19下午3:05:12
 */
public class AuditOpinion {
	
    private String id;

    private ContractProduct contractProduct;

    private String produceUnit;

    private Integer orderAcount;

    private String measuringUnit;

    private String auditUser;

    private Integer companyPrice;

    private String auditOpinion;

    private String unitSubtract;

    private String acountSubtract;

    private Integer checkCompanyPrice;

    private String checkAuditOpinion;

    private String checkUnitSubtract;

    private String checkAcountSubtract;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProduceUnit() {
        return produceUnit;
    }

    public void setProduceUnit(String produceUnit) {
        this.produceUnit = produceUnit == null ? null : produceUnit.trim();
    }

    public String getMeasuringUnit() {
        return measuringUnit;
    }

    public void setMeasuringUnit(String measuringUnit) {
        this.measuringUnit = measuringUnit == null ? null : measuringUnit.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public String getUnitSubtract() {
        return unitSubtract;
    }

    public void setUnitSubtract(String unitSubtract) {
        this.unitSubtract = unitSubtract == null ? null : unitSubtract.trim();
    }

    public String getAcountSubtract() {
        return acountSubtract;
    }

    public void setAcountSubtract(String acountSubtract) {
        this.acountSubtract = acountSubtract == null ? null : acountSubtract.trim();
    }

    public Integer getCheckCompanyPrice() {
		return checkCompanyPrice;
	}

	public void setCheckCompanyPrice(Integer checkCompanyPrice) {
		this.checkCompanyPrice = checkCompanyPrice;
	}

	public String getCheckAuditOpinion() {
        return checkAuditOpinion;
    }

    public void setCheckAuditOpinion(String checkAuditOpinion) {
        this.checkAuditOpinion = checkAuditOpinion == null ? null : checkAuditOpinion.trim();
    }

    public String getCheckUnitSubtract() {
        return checkUnitSubtract;
    }

    public void setCheckUnitSubtract(String checkUnitSubtract) {
        this.checkUnitSubtract = checkUnitSubtract == null ? null : checkUnitSubtract.trim();
    }

    public String getCheckAcountSubtract() {
        return checkAcountSubtract;
    }

    public void setCheckAcountSubtract(String checkAcountSubtract) {
        this.checkAcountSubtract = checkAcountSubtract == null ? null : checkAcountSubtract.trim();
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

	public ContractProduct getContractProduct() {
		return contractProduct;
	}

	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}

	public Integer getOrderAcount() {
		return orderAcount;
	}

	public void setOrderAcount(Integer orderAcount) {
		this.orderAcount = orderAcount;
	}

	public Integer getCompanyPrice() {
		return companyPrice;
	}

	public void setCompanyPrice(Integer companyPrice) {
		this.companyPrice = companyPrice;
	}
    
}