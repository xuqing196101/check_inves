package extract.dao.expert;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    
    /**
     * 
     * Description: 根据职业资格和专家类别查询符合条件的专家
     * 
     * @author zhang shubin
     * @data 2017年9月22日
     * @param 
     * @return
     */
    List<String> findExpertBytypeIdTitle(@Param("qualifcationTitle")String qualifcationTitle,@Param("typeId")String typeId);
}