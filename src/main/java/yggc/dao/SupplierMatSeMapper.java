package yggc.dao;

import yggc.model.SupplierMatSe;

public interface SupplierMatSeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierMatSe record);

    int insertSelective(SupplierMatSe record);

    SupplierMatSe selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierMatSe record);

    int updateByPrimaryKey(SupplierMatSe record);
}