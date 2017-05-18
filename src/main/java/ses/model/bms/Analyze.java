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

	/** 分组名称 1：专家 2：供应商 3：后台管理员 **/
	private String group;

	/** 渲染名称 **/
	private String name;

	/** 渲染值 **/
	private Long value;

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

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

}
