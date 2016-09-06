package yggc.dao.oms;

import yggc.model.oms.Deparent;

public interface DeparentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Deparent record);

    int insertSelective(Deparent record);

    Deparent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Deparent record);

    int updateByPrimaryKeyWithBLOBs(Deparent record);

    int updateByPrimaryKey(Deparent record);
}