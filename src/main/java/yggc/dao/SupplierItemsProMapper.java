package yggc.dao;

import yggc.model.SupplierItemsPro;

public interface SupplierItemsProMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierItemsPro record);

    int insertSelective(SupplierItemsPro record);

    SupplierItemsPro selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierItemsPro record);

    int updateByPrimaryKey(SupplierItemsPro record);
}