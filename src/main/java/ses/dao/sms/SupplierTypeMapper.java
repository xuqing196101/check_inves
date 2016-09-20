package ses.dao.sms;

import java.util.List;

import ses.model.sms.SupplierType;

/**
 * @Title: SupplierTypeMapper
 * @Description: 供应商类型 Mapper
 * @author: Wang Zhaohua
 * @date: 2016-9-18下午2:09:21
 */
public interface SupplierTypeMapper {
	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-18 下午2:09:43
	 * @Description: 根据主键删除数据库的记录
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:10:08
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(SupplierType record);

    /**
     * @Title: insertSelective
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:10:20
     * @Description: 插入不为空的数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(SupplierType record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:10:37
     * @Description: 根据逐渐获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierType
     */
    SupplierType selectByPrimaryKey(String id);
    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:10:56
     * @Description: 更新不为空的数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(SupplierType record);
    /**
     * @Title: updateByPrimaryKey
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:11:12
     * @Description: 根据主键来更新数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(SupplierType record);
    
    /**
     * @Title: findSupplierType
     * @author: Wang Zhaohua
     * @date: 2016-9-18 下午2:21:39
     * @Description: 查询供应商所有类型
     * @param: @return
     * @return: List<SupplierType>
     */
    List<SupplierType> findSupplierType();
}