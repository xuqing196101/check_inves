package ses.service.sms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.QuoteMapper;
import ses.model.sms.Quote;
import ses.service.sms.SupplierQuoteService;
import ses.util.PropertiesUtil;

@Service
public class SupplierQuoteServiceImpl implements SupplierQuoteService {
	
	@Autowired
	private QuoteMapper quoteMapper;
	
	@Override
	public List<Quote> getAllQuote(Quote quote,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Quote> quoteList=quoteMapper.selectByPrimaryKey(quote);
		return quoteList;
	}

	@Override
	public void insert(Quote quote) {
		quoteMapper.insertSelective(quote);
	}
}
