package common.dao;

import java.math.BigDecimal;

import common.model.SystemPV;

/**
 * 
 * Description:统计访问量Mapper
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
public interface SystemPVMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemPV record);

    int insertSelective(SystemPV record);

    SystemPV selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemPV record);

    int updateByPrimaryKey(SystemPV record);
    
    /**
     * 
     * Description:查询总访问数
     * 
     * @author Easong
     * @version 2017年6月13日
     * @return
     */
    BigDecimal selectPvTotalCount();
    
    /**
     * 
     * Description:查询是否存在记录
     * 
     * @author Easong
     * @version 2017年6月13日
     * @param id
     * @return
     */
    Integer selectCountById(Integer id);
}