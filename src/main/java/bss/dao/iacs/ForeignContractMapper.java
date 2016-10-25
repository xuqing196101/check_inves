package bss.dao.iacs;

import bss.model.iacs.ForeignContract;

public interface ForeignContractMapper {
    int deleteByPrimaryKey(String id);

    int insert(ForeignContract record);

    int insertSelective(ForeignContract record);

    ForeignContract selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ForeignContract record);

    int updateByPrimaryKey(ForeignContract record);
}