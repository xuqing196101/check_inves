package extract.dao.expert;

import extract.model.expert.ExpertExtractResult;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractResultMapper {

    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractResult record);

    int insertSelective(ExpertExtractResult record);

    ExpertExtractResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractResult record);

    int updateByPrimaryKey(ExpertExtractResult record);
}