package yggc.dao.sms;

import yggc.model.sms.SupplierFinance;

/**
 * @Title: SupplierFinanceMapper
 * @Description: SupplierFinanceMapper
 * @Company: yggc
 * @author: Poppet_Brook
 * @date: 2016-9-1下午3:45:51
 */
public interface SupplierFinanceMapper {
	/**
	 * @Title: deleteByPrimaryKey
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:45:59
	 * @Description: 根据主键删除数据库的记录
	 * @param: @param id
	 * @param: @return
	 * @return: int
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * @Title: insert
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:46:09
	 * @Description: 插入数据库记录
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int insert(SupplierFinance record);

	/**
	 * @Title: insertSelective
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:46:19
	 * @Description: 选择性保存
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int insertSelective(SupplierFinance record);

	/**
	 * @Title: selectByPrimaryKey
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:47:55
	 * @Description: 根据主键获取一条数据库记录
	 * @param: @param id
	 * @param: @return
	 * @return: SupplierFinance
	 */
	SupplierFinance selectByPrimaryKey(String id);

	/**
	 * @Title: updateByPrimaryKeySelective
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:48:04
	 * @Description: 选择性更新
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(SupplierFinance record);

	/**
	 * @Title: updateByPrimaryKey
	 * @author: Poppet_Brook
	 * @date: 2016-9-1 下午3:48:21
	 * @Description: 根据主键来更新数据库记录
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKey(SupplierFinance record);
}