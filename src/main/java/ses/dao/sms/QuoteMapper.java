package ses.dao.sms;

import java.util.List;

import ses.model.sms.Quote;

public interface QuoteMapper {
    int deleteByPrimaryKey(Quote quote);

    int insert(Quote record);

    int insertSelective(Quote record);

    List<Quote> selectByPrimaryKey(Quote quote);

    int updateByPrimaryKeySelective(Quote record);

    int updateByPrimaryKey(Quote record);
}