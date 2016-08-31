package yggc.dao;

import yggc.model.SupplierRegPerson;

public interface SupplierRegPersonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierRegPerson record);

    int insertSelective(SupplierRegPerson record);

    SupplierRegPerson selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierRegPerson record);

    int updateByPrimaryKey(SupplierRegPerson record);
}