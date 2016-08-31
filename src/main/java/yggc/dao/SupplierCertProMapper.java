package yggc.dao;

import yggc.model.SupplierCertPro;

public interface SupplierCertProMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierCertPro record);

    int insertSelective(SupplierCertPro record);

    SupplierCertPro selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierCertPro record);

    int updateByPrimaryKey(SupplierCertPro record);
}