package ses.dao.sms;

import java.util.List;

import ses.model.sms.Supplier;

/**
 * @Title: SupplierMapper
 * @Description: SupplierMapper
 * @author: Wang Zhaohua
 * @date: 2016-9-7下午6:06:56
 */
public interface SupplierMapper {

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
	int insert(Supplier record);

	/**
	 * @Title: insertSelective
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:38:56
	 * @Description: 选择性插入
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int insertSelective(Supplier record);

	/**
	 * @Title: selectByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:39:27
	 * @Description: 根据主键获取一条数据库记录
	 * @param: @param id
	 * @param: @return
	 * @return: SupplierInfo
	 */
	Supplier selectByPrimaryKey(String id);

	/**
	 * @Title: updateByPrimaryKeySelective
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:40:12
	 * @Description: 根据主键选择性更新
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKeySelective(Supplier record);

	/**
	 * @Title: updateByPrimaryKey
	 * @author: Wang Zhaohua
	 * @date: 2016-9-1 下午3:40:31
	 * @Description: 根据主键更新
	 * @param: @param record
	 * @param: @return
	 * @return: int
	 */
	int updateByPrimaryKey(Supplier record);

	/**
	 * @Title: selectLastInsertId
	 * @author: Wang Zhaohua
	 * @date: 2016-9-5 下午4:12:45
	 * @Description: 查询最后一条插入的数据的 ID
	 * @param: @return
	 * @return: int
	 */
	String selectLastInsertId();
	
	Supplier getSupplier(String id);
	
	/**
	 * @Title: supplierList
	 * @author Xu Qing
	 * @date 2016-9-14 上午11:11:52  
	 * @Description: 供应商列表及条件查询
	 * @param @return      
	 * @return List<Supplier>
	 */
	List<Supplier> findSupplier(Supplier record);
	
	/**
	 * @Title: getAllSupplier
	 * @author Song Biaowei
	 * @date 2016-10-6 下午6:02:17  
	 * @Description: 获取所有的供应商
	 * @param @param record
	 * @param @return      
	 * @return List<Supplier>
	 */
	List<Supplier> getAllSupplier(Supplier record);
	
	
	/**
	 * @Title: querySupplier
	 * @author Song Biaowei
	 * @date 2016-10-5 上午10:22:27  
	 * @Description: 查询一张表，不关联 
	 * @param @param record
	 * @param @return      
	 * @return List<Supplier>
	 */
	List<Supplier> querySupplier(Supplier record);
	
	/**
	 * @Title: querySupplierbyCategory
	 * @author Song Biaowei
	 * @date 2016-10-6 下午5:21:20  
	 * @Description: 查询品目 
	 * @param @param record
	 * @param @return      
	 * @return List<Supplier>
	 */
	List<Supplier> querySupplierbyCategory(Supplier record);
	
	/**
	 * @Title: getCount
	 * @author Xu Qing
	 * @date 2016-9-21 上午10:11:43  
	 * @Description: 根据审核状态获取条数
	 * @param @param supplier
	 * @param @return      
	 * @return Integer
	 */
	Integer getCount(Supplier record);
	
	/**
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-21 下午4:40:27  
	 * @Description: 根据供应商ID更新审核状态
	 * @param @param id
	 * @param @return      
	 * @return Supplier
	 */
	void updateStatus(Supplier record);
	
	int updateSupplierProcurementDep(Supplier supplier);
}