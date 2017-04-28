package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierHistory;

public interface SupplierHistoryService {

	public void add(SupplierHistory supplierHistory);
	
	public SupplierHistory findBySupplierId(SupplierHistory supplierHistory);
	
	public List<SupplierHistory> selectAllBySupplierId(SupplierHistory supplierHistory);
	
	/**
	 *〈简述〉删除历史记录
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierHistory
	 */
	public void delete(SupplierHistory supplierHistory);
	
	/**
	 *〈简述〉
	 * 导入历史记录信息
	 *〈详细描述〉
	 * @author WangHuijie
	 * @param supplierId 供应商Id
	 */
	public void insertHistoryInfo(String supplierId);
	
	/**
     * @Title: updateIsDeleteBySupplierId
     * @author XuQing 
     * @date 2017-4-28 下午3:50:56  
     * @Description:软删除历史记录
     * @param @param supplierId      
     * @return void
     */
    void updateIsDeleteBySupplierId(SupplierHistory supplierHistory);
}
