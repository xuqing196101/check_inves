/**
 * 
 */
package ses.model.sms;

import com.alibaba.fastjson.JSON;

/**
 * @Description:供应商查询条件
 *	 
 * @author Wang Wenshuai
 * @version 2016年9月22日上午10:32:53
 * @since  JDK 1.7
 */
public class SupplierCondition {
	/**
	 * 次数
	 */
	private String number;
	/**
	 * 抽取数量
	 */
	private int  count;
	/**
	 * 地址
	 */
	private String locality;
	/**
	 * 类型
	 */
	private String salesType;
	/**
	 * 产品目录关系
	 */
	private String nexus;
	/**
	 * 抽取项目
	 */
	private String projectName;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getSalesType() {
		return salesType;
	}
	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}
	public String getNexus() {
		return nexus;
	}
	public void setNexus(String nexus) {
		this.nexus = nexus;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return JSON.toJSONString(this);
	}
}
