package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierCondition;

public interface SupplierConditionMapper {
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
    int insert(SupplierCondition record);

    /**
     *
     * @param record
     */
    int insertSelective(SupplierCondition record);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param id
     */
    SupplierCondition selectByPrimaryKey(String id);

    /**
     *
     * @param record
     */
    int updateByPrimaryKeySelective(SupplierCondition record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(SupplierCondition record);
    
    /**
     * @Description:获取集合信息
     *
     * @author Wang Wenshuai
     * @version 2016年9月28日 上午10:47:15  
     * @param @param record
     * @param @return      
     * @return List<SupplierCondition>
     */
    List<SupplierCondition> list(SupplierCondition record);
}