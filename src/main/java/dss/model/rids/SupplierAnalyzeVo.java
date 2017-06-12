package dss.model.rids;

import java.io.Serializable;

/**
 * 
 * Description:供应商统计查询条件
 * 
 * @author Easong
 * @version 2017年6月9日
 * @since JDK1.7
 */
public class SupplierAnalyzeVo implements Serializable{

	/**
	 * SupplierAnalyzeVo.java
	 */
	private static final long serialVersionUID = 1L;

	/** 供应商所在地 **/
	private String address;

	/** 供应商企业性质 ：国企、其他企业**/
	private String businessNature;
	
	/**供应商所属机构**/
	private String orgId;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBusinessNature() {
		return businessNature;
	}

	public void setBusinessNature(String businessNature) {
		this.businessNature = businessNature;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
