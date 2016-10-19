 package bss.model.pqims;

import java.math.BigDecimal;



/**
 * 
 * @Title:Supplier_pqinfo
 * @Description:
 * @author Liyi
 * @date 2016-9-20下午2:41:11
 *
 */
public class Supplier_pqinfo {
	
    private String supplierName;
    private BigDecimal successCount;
    private BigDecimal failCount;
    private String avg;
    
    public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	
	public BigDecimal getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(BigDecimal successCount) {
		this.successCount = successCount;
	}
	public BigDecimal getFailCount() {
		return failCount;
	}
	public void setFailCount(BigDecimal failCount) {
		this.failCount = failCount;
	}
    
    
   
}