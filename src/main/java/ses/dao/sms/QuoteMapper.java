package ses.dao.sms;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import ses.model.sms.Quote;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商报价持久层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
public interface QuoteMapper {
    /**
     *〈简述〉按照主键删除
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体
     * @return int
     */
    int deleteByPrimaryKey(Quote quote);
    /**
     *〈简述〉新增数据
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insert(Quote record);
    /**
     *〈简述〉新增数据某字段为空就不插入
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int insertSelective(Quote record);
    /**
     *〈简述〉条件查找
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体类 
     * @return List<Quote>
     */
    List<Quote> selectByPrimaryKey(Quote quote);
    /**
     *〈简述〉修改数据某字段为空就不修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKeySelective(Quote record);
    /**
     *〈简述〉修改数据某字段为空就不修改
     *〈详细描述〉
     * @author Song Biaowei
     * @param record 实体类
     * @return int
     */
    int updateByPrimaryKey(Quote record);
    /**
     *〈简述〉获取报价次数用时间来分辨
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体类
     * @return List<Date>
     */
    List<Timestamp> selectQuoteCount(Quote quote);
    /**
     *〈简述〉获取报价历史
     *〈详细描述〉
     * @author Song Biaowei
     * @param quote 实体类
     * @return List<Quote>
     */
    List<Quote> selectQuoteHistory(Quote quote);
}