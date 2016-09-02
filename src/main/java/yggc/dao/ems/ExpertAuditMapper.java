package yggc.dao.ems;

import yggc.model.ems.ExpertAudit;

public interface ExpertAuditMapper {
    int deleteByPrimaryKey(String id);

    int insert(ExpertAudit record);

    int insertSelective(ExpertAudit record);

    ExpertAudit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ExpertAudit record);

    int updateByPrimaryKey(ExpertAudit record);
}