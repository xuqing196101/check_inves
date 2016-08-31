package yggc.dao;

import yggc.model.SupplierMatSell;

public interface SupplierMatSellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierMatSell record);

    int insertSelective(SupplierMatSell record);

    SupplierMatSell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierMatSell record);

    int updateByPrimaryKey(SupplierMatSell record);
}