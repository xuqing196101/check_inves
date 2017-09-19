package extract.dao.expert;

import java.util.List;

import extract.model.expert.ExpertExtractTypeInfo;

/**
 * 
 * Description: 专家抽取类型详细品目信息记录
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractTypeInfoMapper {

    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractTypeInfo record);

    int insertSelective(ExpertExtractTypeInfo record);

    ExpertExtractTypeInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractTypeInfo record);

    int updateByPrimaryKey(ExpertExtractTypeInfo record);

	List<ExpertExtractTypeInfo> selectByTypeInfo(ExpertExtractTypeInfo expertExtractTypeInfo);
}