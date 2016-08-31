package yggc.dao;

import yggc.model.SupplierExtracts;

public interface SupplierExtractsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierExtracts record);

    int insertSelective(SupplierExtracts record);

    SupplierExtracts selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierExtracts record);

    int updateByPrimaryKey(SupplierExtracts record);
}