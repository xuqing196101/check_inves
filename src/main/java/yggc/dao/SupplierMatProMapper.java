package yggc.dao;

import yggc.model.SupplierMatPro;

public interface SupplierMatProMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierMatPro record);

    int insertSelective(SupplierMatPro record);

    SupplierMatPro selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierMatPro record);

    int updateByPrimaryKey(SupplierMatPro record);
}