package yggc.dao;

import yggc.model.SupplierInfo;

public interface SupplierInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierInfo record);

    int insertSelective(SupplierInfo record);

    SupplierInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierInfo record);

    int updateByPrimaryKey(SupplierInfo record);
}