package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.BlacklistLog;

public interface BlacklistLogMapper {
    /**
     * 根据主键删除数据库的记录
     *
     * @param id
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(BlacklistLog record);

    /**
     *
     * @param record
     */
    int insertSelective(BlacklistLog record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    BlacklistLog selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(BlacklistLog record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(BlacklistLog record);
    
    List<BlacklistLog> findBlacklistLogBySupplierId(@Param("supplierId")String supplierId);
    
    /**
     * 
     * Description: 根据添加时间查询
     * 
     * @author zhang shubin
     * @data 2017年7月18日
     * @param 
     * @return
     */
    List<BlacklistLog> selectByDate(@Param("start")String start,@Param("end")String end);

    /**
     * 
     * Description: 根据id查询数量
     * 
     * @author zhang shubin
     * @data 2017年7月17日
     * @param 
     * @return
     */
    Integer countById(@Param("id")String id);
}