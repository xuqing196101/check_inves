package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierHistory;
import ses.model.sms.SupplierModify;

public interface SupplierModifyMapper {
	
	/**
	 * @Title: insertSelective
	 * @author XuQing 
	 * @date 2017-2-16 下午4:21:49  
	 * @Description:插入数据
	 * @param @param supplierModify      
	 * @return void
	 */
	void insertSelective (SupplierModify supplierModify);
	
	/**
	 * @Title: selectField
	 * @author XuQing 
	 * @date 2017-2-16 下午4:22:14  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return List<SupplierHistory>
	 */
	List<SupplierModify> selectBySupplierId (SupplierModify supplierModify);
	
	/**
	 * @Title: findBySupplierId
	 * @author XuQing 
	 * @date 2017-2-17 上午10:21:59  
	 * @Description:查询
	 * @param @param supplierModify
	 * @param @return      
	 * @return SupplierModify
	 */
	List<SupplierModify> findBySupplierId (SupplierModify supplierModify);
	
	/**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
    void delete (SupplierModify supplierModify);
    
    /**
     * @Title: add
     * @author XuQing 
     * @date 2017-2-17 下午2:48:40  
     * @Description:插入基本信息
     * @param @param supplierModify      
     * @return void
     */
    void add (SupplierModify supplierModify);
    
    /**
	 * @Title: delete
	 * @author XuQing 
	 * @date 2017-2-16 下午4:24:18  
	 * @Description:删除
	 * @param @param supplierModify      
	 * @return void
	 */
    void deleteByType (SupplierModify supplierModify);
    
    /**
     * 
    * @Title: queryBySupplierId
    * @Description: 根据供应商的ID查询所有的修改
    * author: Li Xiaoxiao 
    * @param @param supplierId
    * @param @return     
    * @return List<SupplierModify>     
    * @throws
     */
	List<SupplierModify> queryBySupplierId (@Param("supplierId")String supplierId);
	
	
	SupplierModify selectById (@Param("id")String id);
	
	
	/**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param SupplierModify      
     * @return void
     */
    void updateIsDeleteBySupplierId(SupplierModify supplierModify);
    /**
     * 
    * @Title: uodateModify
    * @Description: 修改
    * author: Li Xiaoxiao 
    * @param @param supplierModify     
    * @return void     
    * @throws
     */
    void updateModify(SupplierModify supplierModify);

    /**
     * 统计修改数量
     * @param supplierModify
     * @return
     */
	int countBySupplierId(SupplierModify supplierModify);
    
}
