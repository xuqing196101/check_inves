package yggc.dao;

import yggc.model.SupplierCertSe;

public interface SupplierCertSeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierCertSe record);

    int insertSelective(SupplierCertSe record);

    SupplierCertSe selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierCertSe record);

    int updateByPrimaryKey(SupplierCertSe record);
}