package ses.model.bms;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* @ClassName: AnalyzeItem 
* @Description: 统计结果封装项
* @author Easong
* @date 2017年5月3日 下午3:51:33 
*
 */
public class AnalyzeItem {
	
	/**统计名称和值**/
	private List<Analyze> analyzes = new ArrayList<Analyze>();
	
	private Long subtext;

	/**总计**/

	public Long getSubtext() {
		return subtext;
	}

	public List<Analyze> getAnalyzes() {
		return analyzes;
	}

	public void setAnalyzes(List<Analyze> analyzes) {
		this.analyzes = analyzes;
	}

	public void setSubtext(Long subtext) {
		this.subtext = subtext;
	}
	
	
}
