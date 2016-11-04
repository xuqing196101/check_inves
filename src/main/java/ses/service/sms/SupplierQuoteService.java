package ses.service.sms;

import java.util.HashMap;
import java.util.List;

import ses.model.sms.Quote;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

/**
 * @Title: SupplierQuoteService
 * @Description: 供应商报价服务层
 * @author: Song Biaowei
 * @date: 2016-10-28上午10:35:54
 */
public interface SupplierQuoteService {
	
	/**
	 * @Title: getAllQuote
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:35:24  
	 * @Description:获取所有的报价信息 
	 * @param @param quote
	 * @param @param page
	 * @param @return      
	 * @return List<Quote>
	 */
	List<Quote> getAllQuote(Quote quote,Integer page);
	
	/**
	 * @Title: insert
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:35:40  
	 * @Description: 保存报价
	 * @param @param quote      
	 * @return void
	 */
	void insert(List<Quote> listQuote);
	
	/**
	 * @Title: selectByCondition
	 * @author Song Biaowei
	 * @date 2016-11-3 上午10:19:02  
	 * @Description: 可以报价的项目
	 * @param @param map
	 * @param @param page
	 * @param @return      
	 * @return List<ProjectDetail>
	 */
	List<Project> selectByCondition(HashMap<String,Object> map,Integer page);
	
	/**
	 * @Title: selectByPrimaryKey
	 * @author Song Biaowei
	 * @date 2016-11-3 下午3:30:14  
	 * @Description: 查询项目的所有包
	 * @param @param map
	 * @param @param page
	 * @param @return      
	 * @return List<Package>
	 */
	List<Packages> selectByPrimaryKey(HashMap<String,Object> map,Integer page);

}
