package yggc.dao;

import yggc.model.SupplierItems;

public interface SupplierItemsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierItems record);

    int insertSelective(SupplierItems record);

    SupplierItems selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierItems record);

    int updateByPrimaryKey(SupplierItems record);
}