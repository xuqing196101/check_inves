package ses.dao.sms;

import ses.model.sms.OpenBidInfo;

public interface OpenBidInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(OpenBidInfo record);

    int insertSelective(OpenBidInfo record);

    OpenBidInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OpenBidInfo record);

    int updateByPrimaryKey(OpenBidInfo record);
}