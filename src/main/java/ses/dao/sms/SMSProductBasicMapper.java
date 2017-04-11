package ses.dao.sms;

import ses.model.sms.SMSProductBasic;

public interface SMSProductBasicMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductBasic record);

    int insertSelective(SMSProductBasic record);

    SMSProductBasic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductBasic record);

    int updateByPrimaryKey(SMSProductBasic record);
}