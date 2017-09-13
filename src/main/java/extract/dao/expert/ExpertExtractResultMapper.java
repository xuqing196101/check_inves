package extract.dao.expert;

import java.util.List;
import java.util.Map;

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
    
    /**
     * 
     * Description: 条件查询
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    List<ExpertExtractResult> findByConditionIdExpertId(Map<String , Object> map);
    
    /**
     * 
     * Description: 根据条件ID查询专家
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    List<String> findByConditionId(String conditionId);
}