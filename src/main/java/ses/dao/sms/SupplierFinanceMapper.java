package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierFinance;

/**
 * @Title: SupplierFinanceMapper
 * @Description: SupplierFinanceMapper
 * @Company: ses
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
	
	/**
	 * @Title: findFinanceBySupplierId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-14 上午9:53:08
	 * @Description: 根据供应商 ID 查询供应商财务信息
	 * @param: @param supplierId
	 * @param: @return
	 * @return: SupplierFinance
	 */
	List<SupplierFinance> findFinanceBySupplierId(String supplierId);
	
	/**
	 * @Title: deleteFinanceBySupplierId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-14 上午9:53:42
	 * @Description: 根据供应商 ID 删除供应商财务信息
	 * @param: @param supplierId
	 * @param: @return
	 * @return: int
	 */
	int deleteFinanceBySupplierId(String supplierId);
	/**
	 * 
	* @Title: getFinacne
	* @Description: 根据供应商Id和年份查询 
	* author: Li Xiaoxiao 
	* @param @param supplierId
	* @param @param year
	* @param @return     
	* @return SupplierFinance     
	* @throws
	 */
	SupplierFinance getFinacne(@Param("supplierId")String supplierId,@Param("year")String year);
	
	
}