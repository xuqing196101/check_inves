package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierAptituteRecy;

/**
 * 供应商资质证书信息回收接口
 * @author hxg
 * @date 2017-12-20 下午2:58:15
 */
public interface SupplierAptituteRecyService {
	
	List<SupplierAptituteRecy> getBySupplierIdAtLast(String supplierId);
	
	int addSupplierAptituteRecy(SupplierAptituteRecy supplierAptituteRecy);
	
	int delSupplierAptituteRecyById(String id);
}
