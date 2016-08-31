package yggc.dao;

import yggc.model.SupplierTypes;

public interface SupplierTypesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierTypes record);

    int insertSelective(SupplierTypes record);

    SupplierTypes selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierTypes record);

    int updateByPrimaryKey(SupplierTypes record);
}