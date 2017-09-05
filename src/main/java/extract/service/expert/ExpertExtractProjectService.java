package extract.service.expert;

import extract.model.expert.ExpertExtractProject;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractProjectService {

	/**
	 * 
	 * Description: 保存项目信息
	 * 
	 * @author zhang shubin
	 * @data 2017年9月5日
	 * @param 
	 * @return
	 */
	int save(ExpertExtractProject expertExtractProject);
}
