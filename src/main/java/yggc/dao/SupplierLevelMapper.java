package yggc.dao;

import yggc.model.SupplierLevel;

public interface SupplierLevelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierLevel record);

    int insertSelective(SupplierLevel record);

    SupplierLevel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierLevel record);

    int updateByPrimaryKey(SupplierLevel record);
}