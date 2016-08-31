package yggc.dao;

import yggc.model.SupplierFinance;

public interface SupplierFinanceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierFinance record);

    int insertSelective(SupplierFinance record);

    SupplierFinance selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierFinance record);

    int updateByPrimaryKey(SupplierFinance record);
}