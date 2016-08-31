package yggc.dao;

import yggc.model.SupplierBlackList;

public interface SupplierBlackListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierBlackList record);

    int insertSelective(SupplierBlackList record);

    SupplierBlackList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierBlackList record);

    int updateByPrimaryKey(SupplierBlackList record);
}