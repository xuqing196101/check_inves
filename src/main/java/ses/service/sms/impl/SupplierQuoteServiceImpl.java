package ses.service.sms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.QuoteMapper;
import ses.model.sms.Quote;
import ses.service.sms.SupplierQuoteService;
import ses.util.PropertiesUtil;
import bss.dao.ppms.ProjectDetailMapper;
import bss.model.ppms.ProjectDetail;

import com.github.pagehelper.PageHelper;

@Service
public class SupplierQuoteServiceImpl implements SupplierQuoteService {
	
	@Autowired
	private QuoteMapper quoteMapper;
	
	@Autowired
	private ProjectDetailMapper projectDetailMapper;
	
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

	@Override
	public List<ProjectDetail> selectByCondition(HashMap<String, Object> map,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return projectDetailMapper.selectProject(map);
	}
}
