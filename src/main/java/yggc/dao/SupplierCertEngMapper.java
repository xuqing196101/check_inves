package yggc.dao;

import yggc.model.SupplierCertEng;

public interface SupplierCertEngMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierCertEng record);

    int insertSelective(SupplierCertEng record);

    SupplierCertEng selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierCertEng record);

    int updateByPrimaryKey(SupplierCertEng record);
}