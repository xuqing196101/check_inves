package ses.dao.sms;

import ses.model.sms.SMSProductInfo;

public interface SMSProductInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(SMSProductInfo record);

    int insertSelective(SMSProductInfo record);

    SMSProductInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SMSProductInfo record);

    int updateByPrimaryKeyWithBLOBs(SMSProductInfo record);

    int updateByPrimaryKey(SMSProductInfo record);
}