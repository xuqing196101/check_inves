package extract.dao.expert;

import java.util.HashMap;

import extract.model.expert.ExpertExtractCondition;

/**
 * 
 * Description: 专家抽取条件
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractConditionMapper {

    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractCondition record);

    int insertSelective(ExpertExtractCondition record);

    ExpertExtractCondition selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractCondition record);

    int updateByPrimaryKey(ExpertExtractCondition record);

	ExpertExtractCondition getByMap(HashMap<Object, Object> cmap);
}