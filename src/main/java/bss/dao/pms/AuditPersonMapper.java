package bss.dao.pms;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.pms.AuditPerson;

public interface AuditPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(AuditPerson record);

    int insertSelective(AuditPerson record);

    AuditPerson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AuditPerson record);

    int updateByPrimaryKey(AuditPerson record);
    
    List<AuditPerson> query(AuditPerson auditPerson);
    
    int findUserByCondition(HashMap<String,Object> map);
    
    List<AuditPerson> selectByMap(HashMap<String,Object> map);
    
    List<AuditPerson> queryByUserIdAndCid(@Param("userId")String userId,@Param("collectId")String collectId);
}