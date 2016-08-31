package yggc.dao;

import yggc.model.SupplierCertSell;

public interface SupplierCertSellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierCertSell record);

    int insertSelective(SupplierCertSell record);

    SupplierCertSell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierCertSell record);

    int updateByPrimaryKey(SupplierCertSell record);
}