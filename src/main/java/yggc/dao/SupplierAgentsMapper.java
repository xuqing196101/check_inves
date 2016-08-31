package yggc.dao;

import yggc.model.SupplierAgents;

public interface SupplierAgentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierAgents record);

    int insertSelective(SupplierAgents record);

    SupplierAgents selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierAgents record);

    int updateByPrimaryKeyWithBLOBs(SupplierAgents record);

    int updateByPrimaryKey(SupplierAgents record);
}