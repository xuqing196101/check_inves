package ses.dao.sms;

import java.util.List;

import ses.model.sms.ApplyEdit;

public interface ApplyEditMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyEdit record);

    int insertSelective(ApplyEdit record);

    ApplyEdit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyEdit record);

    int updateByPrimaryKey(ApplyEdit record);
    
    List<ApplyEdit> selectByApplyEdit(ApplyEdit record);
}