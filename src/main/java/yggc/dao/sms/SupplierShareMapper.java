package yggc.dao.sms;

import yggc.model.sms.SupplierShare;

/**
 * @Title: SupplierShareMapper
 * @Description: SupplierShareMapper
 * @Company: yggc 
 * @author: Poppet_Brook
 * @date: 2016-9-1下午3:42:17
 */
public interface SupplierShareMapper {
	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:42:01
	 * @Description: 根据主键删除数据库的记录
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:42:29
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(SupplierShare record);

    /**
     * @Title: insertSelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:42:38
     * @Description: 选择性插入数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(SupplierShare record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:42:53
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierShare
     */
    SupplierShare selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:43:07
     * @Description: 根据主键选择性更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(SupplierShare record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:43:19
     * @Description: 根据主键更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(SupplierShare record);
}