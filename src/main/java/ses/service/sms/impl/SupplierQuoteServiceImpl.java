package ses.service.sms.impl;

import java.text.ParseException;
import java.util.Date;
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
import bss.dao.ppms.PackageMapper;
import bss.dao.ppms.ProjectMapper;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

import com.github.pagehelper.PageHelper;

/**
 * 版权：(C) 版权所有 
 * <简述>项目报价服务层实现层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Service
public class SupplierQuoteServiceImpl implements SupplierQuoteService {
    
    /**
     * 报价持久层
     */
    @Autowired
    private QuoteMapper quoteMapper;

    /**
     * 项目持久层
     */
    @Autowired
    private ProjectMapper projectMapper;
    
    /**
     * 分包持久层
     */
    @Autowired
    private PackageMapper  packageMapper; 

    /**
     * sqlSeesionFactory批量插入
     */
    @Autowired
    private SqlSessionFactory sqlSessionFactory; 

    /**
     * @see ses.service.sms.SupplierQuoteService#getAllQuote(ses.model.sms.Quote, java.lang.Integer)
     */
    @Override
    public List<Quote> getAllQuote(Quote quote, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        List<Quote> quoteList = quoteMapper.selectByPrimaryKey(quote);
        return quoteList;
    }

    /**
     * @see ses.service.sms.SupplierQuoteService#insert(java.util.List)
     */
    @Override
    public void insert(List<Quote> listQuote) {
        SqlSession batchSqlSession = null;
        try {
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            //每批commit的个数
            int batchCount = 50; 
            for (int index = 0; index < listQuote.size(); index++){
                Quote quote = listQuote.get(index);
                batchSqlSession.getMapper(QuoteMapper.class).insertSelective(quote);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
    }
    
    /**
     * @see ses.service.sms.SupplierQuoteService#selectByCondition(java.util.HashMap, java.lang.Integer)
     */
    @Override
    public List<Project> selectByCondition(HashMap<String, Object> map, Integer page) {
        PropertiesUtil config = new PropertiesUtil("config.properties");
        PageHelper.startPage(page, Integer.parseInt(config.getString("pageSize")));
        return projectMapper.selectProject(map);
    }
    
    /**
     * @see ses.service.sms.SupplierQuoteService#selectByPrimaryKey(java.util.HashMap, java.lang.Integer)
     */
    @Override
    public List<Packages> selectByPrimaryKey(HashMap<String, Object> map, Integer page) {
        return packageMapper.selectByPrimaryKey(map);
    }
    
    /**
     * @throws ParseException 
     * @see ses.service.sms.SupplierQuoteService#selectQuoteCount(ses.model.sms.Quote)
     */
    @Override
    public List<Date> selectQuoteCount(Quote quote) throws ParseException {
        List<Date> listDate = quoteMapper.selectQuoteCount(quote);
        return listDate;
    }
    
    /**
     * @see ses.service.sms.SupplierQuoteService#selectQuoteHistoryList(ses.model.sms.Quote)
     */
    @Override
    public List<Quote> selectQuoteHistoryList(Quote quote) {
        List<Quote> listQuote = quoteMapper.selectQuoteHistory(quote);
        return listQuote;
    }

    @Override
    public void update(List<Quote> listQuote) {
        quoteMapper.updateByPrimaryKeySelective(listQuote.get(0));
    }
}
