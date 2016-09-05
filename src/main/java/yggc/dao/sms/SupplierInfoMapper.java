package yggc.dao.sms;

import yggc.model.sms.SupplierInfo;

/**
 * @Title: SupplierInfoMapper
 * @Description: SupplierInfoMapper
 * @Company: yggc 
 * @author: Poppet_Brook
 * @date: 2016-9-1下午3:37:44
 */
public interface SupplierInfoMapper {
	
	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:38:14
	 * @Description: 根据主键删除数据库的记录
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
    int deleteByPrimaryKey(String id);

    /**
     * @Title: insert
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:38:31
     * @Description: 插入数据库记录
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insert(SupplierInfo record);

    /**
     * @Title: insertSelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:38:56
     * @Description: 选择性插入
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(SupplierInfo record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:39:27
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierInfo
     */
    SupplierInfo selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:40:12
     * @Description: 根据主键选择性更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(SupplierInfo record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:40:31
     * @Description: 根据主键更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(SupplierInfo record);
}