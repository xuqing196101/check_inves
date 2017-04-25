package bss.model.sstps;

import java.math.BigDecimal;
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

    private BigDecimal orderAcount;

    private String measuringUnit;

    private String auditUser;

    private BigDecimal companyPrice;

    private BigDecimal auditOpinion;

    private BigDecimal unitSubtract;

    private BigDecimal acountSubtract;

    private BigDecimal checkCompanyPrice;

    private BigDecimal checkAuditOpinion;

    private BigDecimal checkUnitSubtract;

    private BigDecimal checkAcountSubtract;

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

	public BigDecimal getOrderAcount() {
		return orderAcount;
	}

	public void setOrderAcount(BigDecimal orderAcount) {
		this.orderAcount = orderAcount;
	}

	public BigDecimal getCompanyPrice() {
		return companyPrice;
	}

	public void setCompanyPrice(BigDecimal companyPrice) {
		this.companyPrice = companyPrice;
	}

	public BigDecimal getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(BigDecimal auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

	public BigDecimal getUnitSubtract() {
		return unitSubtract;
	}

	public void setUnitSubtract(BigDecimal unitSubtract) {
		this.unitSubtract = unitSubtract;
	}

	public BigDecimal getAcountSubtract() {
		return acountSubtract;
	}

	public void setAcountSubtract(BigDecimal acountSubtract) {
		this.acountSubtract = acountSubtract;
	}

	public BigDecimal getCheckCompanyPrice() {
		return checkCompanyPrice;
	}

	public void setCheckCompanyPrice(BigDecimal checkCompanyPrice) {
		this.checkCompanyPrice = checkCompanyPrice;
	}

	public BigDecimal getCheckAuditOpinion() {
		return checkAuditOpinion;
	}

	public void setCheckAuditOpinion(BigDecimal checkAuditOpinion) {
		this.checkAuditOpinion = checkAuditOpinion;
	}

	public BigDecimal getCheckUnitSubtract() {
		return checkUnitSubtract;
	}

	public void setCheckUnitSubtract(BigDecimal checkUnitSubtract) {
		this.checkUnitSubtract = checkUnitSubtract;
	}

	public BigDecimal getCheckAcountSubtract() {
		return checkAcountSubtract;
	}

	public void setCheckAcountSubtract(BigDecimal checkAcountSubtract) {
		this.checkAcountSubtract = checkAcountSubtract;
	}

	
}