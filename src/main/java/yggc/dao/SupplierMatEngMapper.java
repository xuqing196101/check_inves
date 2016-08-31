package yggc.dao;

import yggc.model.SupplierMatEng;

public interface SupplierMatEngMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierMatEng record);

    int insertSelective(SupplierMatEng record);

    SupplierMatEng selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierMatEng record);

    int updateByPrimaryKey(SupplierMatEng record);
}