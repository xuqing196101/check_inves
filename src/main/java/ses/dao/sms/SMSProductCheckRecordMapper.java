package ses.dao.sms;

import ses.model.sms.SMSProductCheckRecord;

public interface SMSProductCheckRecordMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductCheckRecord record);

    int insertSelective(SMSProductCheckRecord record);

    SMSProductCheckRecord selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductCheckRecord record);

    int updateByPrimaryKey(SMSProductCheckRecord record);
}