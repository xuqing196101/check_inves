package ses.dao.sms;

import ses.model.sms.SMSProductArguments;

public interface SMSProductArgumentsMapper {
    int insert(SMSProductArguments record);

    int insertSelective(SMSProductArguments record);
}