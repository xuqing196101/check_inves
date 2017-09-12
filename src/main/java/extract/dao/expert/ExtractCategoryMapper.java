package extract.dao.expert;

import extract.model.expert.ExtractCategory;

/**
 * 
 * Description: 专家抽取品目信息记录表
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExtractCategoryMapper {

	/**
	 * 
	 * Description: 新增
	 * 
	 * @author zhang shubin
	 * @data 2017年9月11日
	 * @param 
	 * @return
	 */
	int insertSelective(ExtractCategory record);
}
