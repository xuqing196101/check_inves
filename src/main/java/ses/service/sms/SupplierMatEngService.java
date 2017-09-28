package ses.service.sms;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatEng;

public interface SupplierMatEngService {
	public void saveOrUpdateSupplierMatEng(Supplier supplier);
	
	/**
	 * 
	 *〈简述〉根据供应商Id查询工程信息
	 *〈详细描述〉
	 * @author myc
	 * @param supplierId 供应商Id
	 * @return
	 */
	public SupplierMatEng getMatEng(String supplierId);

    public String getMatEngIdBySupplierId(String supplierId);
    
    public SupplierMatEng init();
}
