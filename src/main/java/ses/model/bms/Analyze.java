package ses.model.bms;

/**
 * 
* @ClassName: Analyze 
* @Description: 统计结果封装类
* @author Easong
* @date 2017年5月3日 下午3:42:24 
*
 */
public class Analyze {
	
	/** 渲染值 **/
	private Long value;
	
	/** 渲染名称 **/
	private String name;
	

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
