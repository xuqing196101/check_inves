package yggc.dao;

import yggc.model.SupplierApptitute;

public interface SupplierApptituteMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierApptitute record);

    int insertSelective(SupplierApptitute record);

    SupplierApptitute selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierApptitute record);

    int updateByPrimaryKey(SupplierApptitute record);
}