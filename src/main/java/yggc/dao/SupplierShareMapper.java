package yggc.dao;

import yggc.model.SupplierShare;

public interface SupplierShareMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierShare record);

    int insertSelective(SupplierShare record);

    SupplierShare selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierShare record);

    int updateByPrimaryKey(SupplierShare record);
}