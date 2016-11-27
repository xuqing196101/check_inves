package ses.service.sms;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ses.model.sms.Quote;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;

/**
 * 版权：(C) 版权所有 
 * <简述>供应商报价服务层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface SupplierQuoteService {

    /**
     *〈简述〉获取所有的报价信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体类
     * @param page 当前页
     * @return List<Quote>
     */
    List<Quote> getAllQuote(Quote quote, Integer page);

    /**
     *〈简述〉保存报价
     *〈详细描述〉
     * @author Song Biaowei
     * @param listQuote 保存多个包报价
     */
    void insert(List<Quote> listQuote);

    /**
     *〈简述〉可以报价的项目
     *〈详细描述〉
     * @author Song Biaowei
     * @param map 条件
     * @param page 当前页
     * @return List<Project> 
     */
    List<Project> selectByCondition(HashMap<String, Object> map, Integer page);

    /**
     *〈简述〉查询项目的所有包
     *〈详细描述〉
     * @author Song Biaowei
     * @param map 条件
     * @param page 当前页
     * @return List<Packages>
     */
    List<Packages> selectByPrimaryKey(HashMap<String, Object> map, Integer page);

    /**
     *〈简述〉查询报价次数
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体类
     * @return List<Date> 
     * @throws ParseException 
     */
    List<Date> selectQuoteCount(Quote quote) throws ParseException;

    /**
     *〈简述〉查询报价历史记录
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体
     * @return List<Quote>
     */
    List<Quote> selectQuoteHistoryList(Quote quote);
}
