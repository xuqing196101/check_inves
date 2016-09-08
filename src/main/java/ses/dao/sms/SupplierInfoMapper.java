package ses.dao.sms;

import ses.model.sms.SupplierInfo;

/**
 * @Title: SupplierInfoMapper
 * @Description: SupplierInfoMapper
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:06:56
 */
public interface SupplierInfoMapper {

	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-7 下午6:07:01
	 * @Description: 根据主键删除
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * @Title: insert
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:38:31
	 * @Description: 插入数据库记录
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int insert(SupplierInfo record);

	/**
	 * @Title: insertSelective
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:38:56
	 * @Description: 选择性插入
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int insertSelective(SupplierInfo record);

	/**
	 * @Title: selectByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:39:27
	 * @Description: 根据主键获取一条数据库记录
	 * @param: @param id
	 * @param: @return
	 * @return: SupplierInfo
	 */
	SupplierInfo selectByPrimaryKey(String id);

	/**
	 * @Title: updateByPrimaryKeySelective
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:40:12
	 * @Description: 根据主键选择性更新
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(SupplierInfo record);

	/**
	 * @Title: updateByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:40:31
	 * @Description: 根据主键更新
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKey(SupplierInfo record);

	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:12:45
	 * @Description: 查询最后一条插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	String selectLastInsertId();
}