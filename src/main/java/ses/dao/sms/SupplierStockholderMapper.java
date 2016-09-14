package ses.dao.sms;

import ses.model.sms.SupplierStockholder;

/**
 * @Title: SupplierShareMapper
 * @Description: SupplierShareMapper
 * @Company: ses 
 * @author: Poppet_Brook
 * @date: 2016-9-1下午3:42:17
 */
public interface SupplierStockholderMapper {
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
    int insert(SupplierStockholder record);

    /**
     * @Title: insertSelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:42:38
     * @Description: 选择性插入数据
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int insertSelective(SupplierStockholder record);

    /**
     * @Title: selectByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:42:53
     * @Description: 根据主键获取一条数据库记录
     * @param: @param id
     * @param: @return
     * @return: SupplierShare
     */
    SupplierStockholder selectByPrimaryKey(String id);

    /**
     * @Title: updateByPrimaryKeySelective
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:43:07
     * @Description: 根据主键选择性更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKeySelective(SupplierStockholder record);

    /**
     * @Title: updateByPrimaryKey
     * @author: Poppet_Brook
     * @date: 2016-9-1 下午3:43:19
     * @Description: 根据主键更新
     * @param: @param record
     * @param: @return
     * @return: int
     */
    int updateByPrimaryKey(SupplierStockholder record);
    
    /**
     * @Title: findStockholderBySupplierId
     * @author: Wang Zhaohua
     * @date: 2016-9-14 上午10:00:45
     * @Description: 根据供应商 ID 查询供应商股东信息
     * @param: @param supplierId
     * @param: @return
     * @return: SupplierStockholder
     */
    SupplierStockholder findStockholderBySupplierId(String supplierId);
    
    /**
     * @Title: deleteStockholderBySupplierId
     * @author: Wang Zhaohua
     * @date: 2016-9-14 上午10:01:32
     * @Description: 根据供应商 ID 删除供应商股东信息
     * @param: @param supplierId
     * @param: @return
     * @return: int
     */
    int deleteStockholderBySupplierId(String supplierId);
}