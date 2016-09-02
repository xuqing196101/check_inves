package yggc.dao.ems;

import yggc.model.ems.ExpertBlackList;

public interface ExpertBlackListMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertBlackList record);

    int insertSelective(ExpertBlackList record);

    ExpertBlackList selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertBlackList record);

    int updateByPrimaryKey(ExpertBlackList record);
}