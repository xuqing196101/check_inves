package bss.model.ob;

import java.io.Serializable;

/**
 * 
* @ClassName: OBProjectSupplierVo 
* @Description: 供应商列表查询数据封装实体
* @author Easong
* @date 2017年4月13日 上午10:45:11 
*
 */
public class OBProjectSupplierVo extends OBProjectSupplier implements Serializable{
	
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	
	/**
	 * 标题名称
	 */
	private String name;
	
	/**
	 * 发布时间
	 */
	private String createTime;
	
	/**OBProject:status 和 OBProjectSupplier:remark**/
	private String queryStatus;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQueryStatus() {
		return queryStatus;
	}

	public void setQueryStatus(String queryStatus) {
		this.queryStatus = queryStatus;
	}
	
	
}
