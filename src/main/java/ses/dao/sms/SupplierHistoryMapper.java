package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryMapper {
	void insertSelective (SupplierHistory supplierHistory);
	
	/**
	 * 
	* @Title: inserActive
	* @Description: 无自动生成主键
	* author: Li Xiaoxiao 
	* @param @param supplierHistory     
	* @return void     
	* @throws
	 */
	void inserActive(SupplierHistory supplierHistory);
	
	SupplierHistory selectBySupplierId(SupplierHistory supplierHistory);
	
	List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory);
	
	List<SupplierHistory> findListBySupplierId(SupplierHistory supplierHistory);
	
	/**
     *〈简述〉删除历史记录
     *〈详细描述〉
     * @author WangHuijie
     * @param supplierHistory
     */
    void delete(SupplierHistory supplierHistory);
    /**
     * 
    * @Title: selectAllBySupplierId
    * @Description: 根据供应商ID查询
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @return     
    * @return List<SupplierHistory>     
    * @throws
     */
    List<SupplierHistory> queryBySupplierId(@Param("supplierId")String supplierId);
    
    SupplierHistory queryById(@Param("id")String id);
    
    /**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param supplierHistory      
     * @return void
     */
    void updateIsDeleteBySupplierId(SupplierHistory supplierHistory);
    
}
