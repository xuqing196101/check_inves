package bss.dao.pms;

import java.util.List;

import bss.model.pms.AuditPerson;

public interface AuditPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditPerson record);

    int insertSelective(AuditPerson record);

    AuditPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditPerson record);

    int updateByPrimaryKey(AuditPerson record);
    
    List<AuditPerson> query(AuditPerson auditPerson);
}