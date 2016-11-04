package ses.service.sms.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.sms.QuoteMapper;
import ses.model.sms.Quote;
import ses.service.sms.SupplierQuoteService;
import ses.util.PropertiesUtil;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.pms.PurchaseRequired;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

import com.github.pagehelper.PageHelper;

@Service
public class SupplierQuoteServiceImpl implements SupplierQuoteService {
	
	@Autowired
	private QuoteMapper quoteMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private PackageMapper  packageMapper ; 
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory; 
	
	@Override
	public List<Quote> getAllQuote(Quote quote,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<Quote> quoteList=quoteMapper.selectByPrimaryKey(quote);
		return quoteList;
	}

	@Override
	public void insert(List<Quote> listQuote) {
		 SqlSession batchSqlSession = null;
	        try{
	            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	            int batchCount = 500;//每批commit的个数
	            for(int index = 0; index < listQuote.size();index++){
	            	Quote quote = listQuote.get(index);
	                batchSqlSession.getMapper(QuoteMapper.class).insertSelective(quote);
	                if(index !=0 && index%batchCount == 0){
	                    batchSqlSession.commit();
	                }
	            }
	            batchSqlSession.commit();
	        }catch (Exception e){
	            e.printStackTrace();
	        }finally {
	            if(batchSqlSession != null){
	                batchSqlSession.close();
	            }
	        }
	}

	@Override
	public List<Project> selectByCondition(HashMap<String, Object> map,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return projectMapper.selectProject(map);
	}

	@Override
	public List<Packages> selectByPrimaryKey(HashMap<String, Object> map,
			Integer page) {
		return packageMapper.selectByPrimaryKey(map);
	}
}
