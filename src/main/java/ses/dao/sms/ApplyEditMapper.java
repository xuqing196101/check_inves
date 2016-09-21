package ses.dao.sms;

import ses.model.sms.ApplyEdit;

public interface ApplyEditMapper {
    int deleteByPrimaryKey(String id);

    int insert(ApplyEdit record);

    int insertSelective(ApplyEdit record);

    ApplyEdit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ApplyEdit record);

    int updateByPrimaryKey(ApplyEdit record);
}