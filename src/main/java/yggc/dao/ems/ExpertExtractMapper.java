package yggc.dao.ems;

import yggc.model.ems.ExpertExtract;

public interface ExpertExtractMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertExtract record);

    int insertSelective(ExpertExtract record);

    ExpertExtract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertExtract record);

    int updateByPrimaryKey(ExpertExtract record);
}