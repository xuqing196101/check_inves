package common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Description:计数查询实体封装
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
public class SystemPVVO extends SystemPV implements Serializable{

	/**
	 * SystemPVVO.java
	 */
	private static final long serialVersionUID = 1L;
	
	/**总访问量**/
	private BigDecimal totalCount;
	
	/**登录人**/
	private String loginName;

	public BigDecimal getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(BigDecimal totalCount) {
		this.totalCount = totalCount;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
