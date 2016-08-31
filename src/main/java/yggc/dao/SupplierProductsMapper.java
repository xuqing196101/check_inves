package yggc.dao;

import yggc.model.SupplierProducts;

public interface SupplierProductsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierProducts record);

    int insertSelective(SupplierProducts record);

    SupplierProducts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierProducts record);

    int updateByPrimaryKey(SupplierProducts record);
}