package yggc.dao;

import yggc.model.SupplierAudit;

public interface SupplierAuditMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SupplierAudit record);

    int insertSelective(SupplierAudit record);

    SupplierAudit selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SupplierAudit record);

    int updateByPrimaryKey(SupplierAudit record);
}