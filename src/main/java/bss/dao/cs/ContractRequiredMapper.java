package bss.dao.cs;

import bss.model.cs.ContractRequired;

public interface ContractRequiredMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractRequired record);

    int insertSelective(ContractRequired record);

    ContractRequired selectConRequByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractRequired record);

    int updateByPrimaryKey(ContractRequired record);
}