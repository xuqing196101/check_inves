package yggc.dao;

import yggc.model.SupplierItemsSe;

public interface SupplierItemsSeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierItemsSe record);

    int insertSelective(SupplierItemsSe record);

    SupplierItemsSe selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierItemsSe record);

    int updateByPrimaryKey(SupplierItemsSe record);
}