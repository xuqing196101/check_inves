package ses.model.bms;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Description: 统计封装类VO
 * 
 * @author Easong
 * @version 2017年6月6日
 * @since JDK1.7
 */
public class AnalyzeVo implements Serializable{

	/**
	 * AnalyzeVo.java
	 */
	private static final long serialVersionUID = 1L;

	/** 采购机构 **/
	private String name;
	
	/** 采购金额 **/
	private BigDecimal money;
	
	/** 采购数量**/
	private BigDecimal count;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getCount() {
		return count;
	}

	public void setCount(BigDecimal count) {
		this.count = count;
	}
	
}
