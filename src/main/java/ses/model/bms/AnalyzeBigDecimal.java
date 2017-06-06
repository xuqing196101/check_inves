package ses.model.bms;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 
 * @ClassName: Analyze
 * @Description: 统计结果封装类
 * @author Easong
 * @date 2017年5月3日 下午3:42:24
 * 
 */
public class AnalyzeBigDecimal implements Serializable{
	/**
	 * AnalyzeBigDecimal.java
	 */
	private static final long serialVersionUID = 1L;

	/** 分组名称 1：专家 2：供应商 3：后台管理员 **/
	private String group;

	/** 渲染名称 **/
	private String name;

	/** 渲染值 **/
	private BigDecimal value;
	
	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
}
