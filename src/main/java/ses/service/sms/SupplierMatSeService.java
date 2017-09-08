package ses.service.sms;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatServe;

public interface SupplierMatSeService {
	public void saveOrUpdateSupplierMatSe(Supplier supplier);
	
	/**
	 * 
	 *〈简述〉根据供应商Id查询服务类
	 *〈详细描述〉
	 * @author myc
	 * @param supplierId 供应商Id
	 * @return
	 */
	public SupplierMatServe getMatserver(String supplierId);

    public String getMatSeIdBySupplierId(String supplierId);
    
    public SupplierMatServe init();
}
