package ses.service.ems;

import java.util.List;
import java.util.Map;

import ses.model.ems.ExpertTitle;

public interface ExpertTitleService {
	/**
	 * 1.获取所有模板对象
	 */
	List<ExpertTitle> getAll(Map<String, Object> map);
	/**
	 * 2.添加模板
	 */
	public void add(ExpertTitle expertTitle);
	
	/**
	 * 3.更新模板
	 */
	public void update(ExpertTitle expertTitle);

}
