 package bss.model.pqims;

import java.math.BigDecimal;

import ses.model.sms.Supplier;

/**
 * 
 * @Title:SupplierPqrecord
 * @Description:
 * @author Liyi
 * @date 2016-9-20下午2:41:11
 *
 */
public class SupplierPqrecord {
	
	/*
	 * 主键
	 */
	private String id;
	
	/*
	 * 供应商
	 */
    private Supplier supplier;
    
    /*
     * 质检合格次数
     */
    private Integer successedCount;
    
    /*
     * 质检不合格次数
     */
    private Integer failedCount;
    
    /*
     * 质检合格百分比
     */
    private String successedAvg;
    
    private String supplierName;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Integer getSuccessedCount() {
		return successedCount;
	}
	public void setSuccessedCount(Integer successedCount) {
		this.successedCount = successedCount;
	}
	public Integer getFailedCount() {
		return failedCount;
	}
	public void setFailedCount(Integer failedCount) {
		this.failedCount = failedCount;
	}
	public String getSuccessedAvg() {
		return successedAvg;
	}
	public void setSuccessedAvg(String successedAvg) {
		this.successedAvg = successedAvg;
	}
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
    
}