package bss.model.sstps;

import java.util.Date;

/**
* @Title:ProductQuota 
* @Description: 产品工时定额明细
* @author Shen Zhenfei
* @date 2016-10-18上午10:04:41
 */
public class ProductQuota {
	
    private String id;

    private ContractProduct contractProduct;

    private String partsName;

    private String partsDrawingCode;

    private String processName;

    private Integer offer;

    private Integer ratify;

    private Integer check;

    private Integer processingOffer;

    private Integer processingRatify;

    private Integer processingCheck;

    private Integer assemblyOffer;

    private Integer assemblyRatify;

    private Integer assemblyCheck;

    private Integer debuggingOffer;

    private Integer debuggingRatify;

    private Integer debuggingCheck;

    private Integer testOffer;

    private Integer testRatify;

    private Integer testCheck;

    private Integer otherOffer;

    private Integer otherRatify;

    private Integer otherCheck;

    private Integer subtotalOffer;

    private Integer subtotalRatify;

    private Integer subtotalCheck;

    private String measuringUnit;

    private Integer assortOffer;

    private Integer assortRatify;

    private Integer assortCheck;

    private Integer approvedOffer;

    private Integer approvedRatify;

    private Integer approvedSubtract;

    private Integer approvedCheck;

    private String remark;

    private Date createdAt;

    private Date updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
   
    public ContractProduct getContractProduct() {
		return contractProduct;
	}

	public void setContractProduct(ContractProduct contractProduct) {
		this.contractProduct = contractProduct;
	}

	public String getPartsName() {
		return partsName;
	}

	public void setPartsName(String partsName) {
		this.partsName = partsName;
	}

	public String getPartsDrawingCode() {
		return partsDrawingCode;
	}

	public void setPartsDrawingCode(String partsDrawingCode) {
		this.partsDrawingCode = partsDrawingCode;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Integer getOffer() {
		return offer;
	}

	public void setOffer(Integer offer) {
		this.offer = offer;
	}

	public Integer getRatify() {
		return ratify;
	}

	public void setRatify(Integer ratify) {
		this.ratify = ratify;
	}

	public Integer getCheck() {
		return check;
	}

	public void setCheck(Integer check) {
		this.check = check;
	}

	public Integer getProcessingOffer() {
		return processingOffer;
	}

	public void setProcessingOffer(Integer processingOffer) {
		this.processingOffer = processingOffer;
	}

	public Integer getProcessingRatify() {
		return processingRatify;
	}

	public void setProcessingRatify(Integer processingRatify) {
		this.processingRatify = processingRatify;
	}

	public Integer getProcessingCheck() {
		return processingCheck;
	}

	public void setProcessingCheck(Integer processingCheck) {
		this.processingCheck = processingCheck;
	}

	public Integer getAssemblyOffer() {
		return assemblyOffer;
	}

	public void setAssemblyOffer(Integer assemblyOffer) {
		this.assemblyOffer = assemblyOffer;
	}

	public Integer getAssemblyRatify() {
		return assemblyRatify;
	}

	public void setAssemblyRatify(Integer assemblyRatify) {
		this.assemblyRatify = assemblyRatify;
	}

	public Integer getAssemblyCheck() {
		return assemblyCheck;
	}

	public void setAssemblyCheck(Integer assemblyCheck) {
		this.assemblyCheck = assemblyCheck;
	}

	public Integer getDebuggingOffer() {
		return debuggingOffer;
	}

	public void setDebuggingOffer(Integer debuggingOffer) {
		this.debuggingOffer = debuggingOffer;
	}

	public Integer getDebuggingRatify() {
		return debuggingRatify;
	}

	public void setDebuggingRatify(Integer debuggingRatify) {
		this.debuggingRatify = debuggingRatify;
	}

	public Integer getDebuggingCheck() {
		return debuggingCheck;
	}

	public void setDebuggingCheck(Integer debuggingCheck) {
		this.debuggingCheck = debuggingCheck;
	}

	public Integer getTestOffer() {
		return testOffer;
	}

	public void setTestOffer(Integer testOffer) {
		this.testOffer = testOffer;
	}

	public Integer getTestRatify() {
		return testRatify;
	}

	public void setTestRatify(Integer testRatify) {
		this.testRatify = testRatify;
	}

	public Integer getTestCheck() {
		return testCheck;
	}

	public void setTestCheck(Integer testCheck) {
		this.testCheck = testCheck;
	}

	public Integer getOtherOffer() {
		return otherOffer;
	}

	public void setOtherOffer(Integer otherOffer) {
		this.otherOffer = otherOffer;
	}

	public Integer getOtherRatify() {
		return otherRatify;
	}

	public void setOtherRatify(Integer otherRatify) {
		this.otherRatify = otherRatify;
	}

	public Integer getOtherCheck() {
		return otherCheck;
	}

	public void setOtherCheck(Integer otherCheck) {
		this.otherCheck = otherCheck;
	}

	public Integer getSubtotalOffer() {
		return subtotalOffer;
	}

	public void setSubtotalOffer(Integer subtotalOffer) {
		this.subtotalOffer = subtotalOffer;
	}

	public Integer getSubtotalRatify() {
		return subtotalRatify;
	}

	public void setSubtotalRatify(Integer subtotalRatify) {
		this.subtotalRatify = subtotalRatify;
	}

	public Integer getSubtotalCheck() {
		return subtotalCheck;
	}

	public void setSubtotalCheck(Integer subtotalCheck) {
		this.subtotalCheck = subtotalCheck;
	}

	public String getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(String measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public Integer getAssortOffer() {
		return assortOffer;
	}

	public void setAssortOffer(Integer assortOffer) {
		this.assortOffer = assortOffer;
	}

	public Integer getAssortRatify() {
		return assortRatify;
	}

	public void setAssortRatify(Integer assortRatify) {
		this.assortRatify = assortRatify;
	}

	public Integer getAssortCheck() {
		return assortCheck;
	}

	public void setAssortCheck(Integer assortCheck) {
		this.assortCheck = assortCheck;
	}

	public Integer getApprovedOffer() {
		return approvedOffer;
	}

	public void setApprovedOffer(Integer approvedOffer) {
		this.approvedOffer = approvedOffer;
	}

	public Integer getApprovedRatify() {
		return approvedRatify;
	}

	public void setApprovedRatify(Integer approvedRatify) {
		this.approvedRatify = approvedRatify;
	}

	public Integer getApprovedSubtract() {
		return approvedSubtract;
	}

	public void setApprovedSubtract(Integer approvedSubtract) {
		this.approvedSubtract = approvedSubtract;
	}

	public Integer getApprovedCheck() {
		return approvedCheck;
	}

	public void setApprovedCheck(Integer approvedCheck) {
		this.approvedCheck = approvedCheck;
	}

	public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
}