package extract.dao.expert;

import extract.model.expert.ExpertExtractProject;

public interface ExpertExtractProjectMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertExtractProject record);

    int insertSelective(ExpertExtractProject record);

    ExpertExtractProject selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtractProject record);

    int updateByPrimaryKey(ExpertExtractProject record);
}