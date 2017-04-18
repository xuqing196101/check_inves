package bss.model.sstps;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

/**
* @Title:ProductQuota 
* @Description: 产品工时定额明细
* @author Shen Zhenfei
* @date 2016-10-18上午10:04:41
 */
public class ProductQuota {
	
    private String id;

    private ContractProduct contractProduct;

    @NotBlank(message = "零件部件名称不能为空")
    private String partsName;

    @NotBlank(message = "零件部件图号不能为空")
    private String partsDrawingCode;

    @NotBlank(message = "工序名称不能为空")
    private String processName;

    private BigDecimal offer;

    /*
     * 核定-准结工时
     */
    private BigDecimal ratify;

    /*
     * 复核-准结工时
     */
    private BigDecimal check;

    private BigDecimal processingOffer;

    /*
     * 核定-加工工时
     */
    private BigDecimal processingRatify;

    /*
     * 复核-加工工时
     */
    private BigDecimal processingCheck;

    private BigDecimal assemblyOffer;

    private BigDecimal assemblyRatify;

    private BigDecimal assemblyCheck;

    private BigDecimal debuggingOffer;

    private BigDecimal debuggingRatify;

    private BigDecimal debuggingCheck;

    private BigDecimal testOffer;

    private BigDecimal testRatify;

    private BigDecimal testCheck;

    private BigDecimal otherOffer;

    private BigDecimal otherRatify;

    private BigDecimal otherCheck;

    private BigDecimal subtotalOffer;

    private BigDecimal subtotalRatify;

    private BigDecimal subtotalCheck;

    private BigDecimal measuringUnit;

    private BigDecimal assortOffer;

    private BigDecimal assortRatify;

    private BigDecimal assortCheck;

    private BigDecimal approvedOffer;

    private BigDecimal approvedRatify;

    private BigDecimal approvedSubtract;

    private BigDecimal approvedCheck;

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

	
	public BigDecimal getOffer() {
		return offer;
	}

	public void setOffer(BigDecimal offer) {
		this.offer = offer;
	}

	public BigDecimal getRatify() {
		return ratify;
	}

	public void setRatify(BigDecimal ratify) {
		this.ratify = ratify;
	}

	public BigDecimal getCheck() {
		return check;
	}

	public void setCheck(BigDecimal check) {
		this.check = check;
	}

	public BigDecimal getProcessingOffer() {
		return processingOffer;
	}

	public void setProcessingOffer(BigDecimal processingOffer) {
		this.processingOffer = processingOffer;
	}

	public BigDecimal getProcessingRatify() {
		return processingRatify;
	}

	public void setProcessingRatify(BigDecimal processingRatify) {
		this.processingRatify = processingRatify;
	}

	public BigDecimal getProcessingCheck() {
		return processingCheck;
	}

	public void setProcessingCheck(BigDecimal processingCheck) {
		this.processingCheck = processingCheck;
	}

	public BigDecimal getAssemblyOffer() {
		return assemblyOffer;
	}

	public void setAssemblyOffer(BigDecimal assemblyOffer) {
		this.assemblyOffer = assemblyOffer;
	}

	public BigDecimal getAssemblyRatify() {
		return assemblyRatify;
	}

	public void setAssemblyRatify(BigDecimal assemblyRatify) {
		this.assemblyRatify = assemblyRatify;
	}

	public BigDecimal getAssemblyCheck() {
		return assemblyCheck;
	}

	public void setAssemblyCheck(BigDecimal assemblyCheck) {
		this.assemblyCheck = assemblyCheck;
	}

	public BigDecimal getDebuggingOffer() {
		return debuggingOffer;
	}

	public void setDebuggingOffer(BigDecimal debuggingOffer) {
		this.debuggingOffer = debuggingOffer;
	}

	public BigDecimal getDebuggingRatify() {
		return debuggingRatify;
	}

	public void setDebuggingRatify(BigDecimal debuggingRatify) {
		this.debuggingRatify = debuggingRatify;
	}

	public BigDecimal getDebuggingCheck() {
		return debuggingCheck;
	}

	public void setDebuggingCheck(BigDecimal debuggingCheck) {
		this.debuggingCheck = debuggingCheck;
	}

	public BigDecimal getTestOffer() {
		return testOffer;
	}

	public void setTestOffer(BigDecimal testOffer) {
		this.testOffer = testOffer;
	}

	public BigDecimal getTestRatify() {
		return testRatify;
	}

	public void setTestRatify(BigDecimal testRatify) {
		this.testRatify = testRatify;
	}

	public BigDecimal getTestCheck() {
		return testCheck;
	}

	public void setTestCheck(BigDecimal testCheck) {
		this.testCheck = testCheck;
	}

	public BigDecimal getOtherOffer() {
		return otherOffer;
	}

	public void setOtherOffer(BigDecimal otherOffer) {
		this.otherOffer = otherOffer;
	}

	public BigDecimal getOtherRatify() {
		return otherRatify;
	}

	public void setOtherRatify(BigDecimal otherRatify) {
		this.otherRatify = otherRatify;
	}

	public BigDecimal getOtherCheck() {
		return otherCheck;
	}

	public void setOtherCheck(BigDecimal otherCheck) {
		this.otherCheck = otherCheck;
	}

	public BigDecimal getSubtotalOffer() {
		return subtotalOffer;
	}

	public void setSubtotalOffer(BigDecimal subtotalOffer) {
		this.subtotalOffer = subtotalOffer;
	}

	public BigDecimal getSubtotalRatify() {
		return subtotalRatify;
	}

	public void setSubtotalRatify(BigDecimal subtotalRatify) {
		this.subtotalRatify = subtotalRatify;
	}

	public BigDecimal getSubtotalCheck() {
		return subtotalCheck;
	}

	public void setSubtotalCheck(BigDecimal subtotalCheck) {
		this.subtotalCheck = subtotalCheck;
	}

	public BigDecimal getMeasuringUnit() {
		return measuringUnit;
	}

	public void setMeasuringUnit(BigDecimal measuringUnit) {
		this.measuringUnit = measuringUnit;
	}

	public BigDecimal getAssortOffer() {
		return assortOffer;
	}

	public void setAssortOffer(BigDecimal assortOffer) {
		this.assortOffer = assortOffer;
	}

	public BigDecimal getAssortRatify() {
		return assortRatify;
	}

	public void setAssortRatify(BigDecimal assortRatify) {
		this.assortRatify = assortRatify;
	}

	public BigDecimal getAssortCheck() {
		return assortCheck;
	}

	public void setAssortCheck(BigDecimal assortCheck) {
		this.assortCheck = assortCheck;
	}

	public BigDecimal getApprovedOffer() {
		return approvedOffer;
	}

	public void setApprovedOffer(BigDecimal approvedOffer) {
		this.approvedOffer = approvedOffer;
	}

	public BigDecimal getApprovedRatify() {
		return approvedRatify;
	}

	public void setApprovedRatify(BigDecimal approvedRatify) {
		this.approvedRatify = approvedRatify;
	}

	public BigDecimal getApprovedSubtract() {
		return approvedSubtract;
	}

	public void setApprovedSubtract(BigDecimal approvedSubtract) {
		this.approvedSubtract = approvedSubtract;
	}

	public BigDecimal getApprovedCheck() {
		return approvedCheck;
	}

	public void setApprovedCheck(BigDecimal approvedCheck) {
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