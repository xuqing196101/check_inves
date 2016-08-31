package yggc.dao;

import yggc.model.SupplierItemsSell;

public interface SupplierItemsSellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierItemsSell record);

    int insertSelective(SupplierItemsSell record);

    SupplierItemsSell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierItemsSell record);

    int updateByPrimaryKey(SupplierItemsSell record);
}