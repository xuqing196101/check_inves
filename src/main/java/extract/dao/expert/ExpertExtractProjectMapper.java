package extract.dao.expert;

import extract.model.expert.ExpertExtractProject;

/**
 * 
 * Description: 专家抽取项目信息管理
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface ExpertExtractProjectMapper {

    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractProject record);

    /**
     * 
     * Description: 保存非空数据
     * 
     * @author zhang shubin
     * @data 2017年9月5日
     * @param
     * @return
     */
    int insertSelective(ExpertExtractProject record);

    ExpertExtractProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractProject record);

    int updateByPrimaryKey(ExpertExtractProject record);
}