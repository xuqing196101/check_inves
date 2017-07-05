package dss.model.rids;

import java.io.Serializable;

/**
 * 
 * Description: 专家统计查询条件
 * 
 * @author Easong
 * @version 2017年6月8日
 * @since JDK1.7
 */
public class ExpertAnalyzeVo implements Serializable{

	/**
	 * ExpertAnalyzeVo.java
	 */
	private static final long serialVersionUID = 1L;
	
	/**专家所在地ID**/
	private String address;
	
	/**专家类型ID**/
	private String expertsTypeId;
	
	/**军地类型：军队、地方**/
	private String expertsFrom;
	
	/** 所属采购机构ID **/
	private String orgId;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getExpertsTypeId() {
		return expertsTypeId;
	}

	public void setExpertsTypeId(String expertsTypeId) {
		this.expertsTypeId = expertsTypeId;
	}

	public String getExpertsFrom() {
		return expertsFrom;
	}

	public void setExpertsFrom(String expertsFrom) {
		this.expertsFrom = expertsFrom;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
