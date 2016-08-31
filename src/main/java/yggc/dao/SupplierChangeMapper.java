package yggc.dao;

import yggc.model.SupplierChange;

public interface SupplierChangeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierChange record);

    int insertSelective(SupplierChange record);

    SupplierChange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierChange record);

    int updateByPrimaryKey(SupplierChange record);
}