package dss.model.rids;

/**
 * 
 * Description:供应商统计查询条件
 * 
 * @author Easong
 * @version 2017年6月9日
 * @since JDK1.7
 */
public class SupplierAnalyzeVo {

	/** 供应商所在地 **/
	private String address;

	/** 供应商企业性质 ：国企、其他企业**/
	private String businessNature;

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

}
