package ses.dao.sms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryMapper {
	void insertSelective (SupplierHistory supplierHistory);
	
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
    
    
}
