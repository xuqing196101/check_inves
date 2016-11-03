package bss.dao.pms;

import java.util.List;

import bss.model.pms.AuditParam;

public interface AuditParamMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditParam record);

    int insertSelective(AuditParam record);

    AuditParam selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditParam record);

    int updateByPrimaryKey(AuditParam record);
    
    List<AuditParam> query(AuditParam auditParam);
}