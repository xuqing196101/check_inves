package yggc.dao;

import yggc.model.SupplierItemsEng;

public interface SupplierItemsEngMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierItemsEng record);

    int insertSelective(SupplierItemsEng record);

    SupplierItemsEng selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierItemsEng record);

    int updateByPrimaryKey(SupplierItemsEng record);
}