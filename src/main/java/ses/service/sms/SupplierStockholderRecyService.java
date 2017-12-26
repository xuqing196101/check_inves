package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierStockholderRecy;

/**
 * 供应商股东信息回收接口
 * @author hxg
 * @date 2017-12-19 下午7:52:57
 */
public interface SupplierStockholderRecyService {

	List<SupplierStockholderRecy> getBySupplierIdAtLast(String supplierId);
	
	int addSupplierStockholderRecy(SupplierStockholderRecy supplierStockholderRecy);
	
	int delSupplierStockholderRecyById(String id);

}
