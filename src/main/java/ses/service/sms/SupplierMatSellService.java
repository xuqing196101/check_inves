package ses.service.sms;

import ses.model.sms.Supplier;
import ses.model.sms.SupplierMatSell;

public interface SupplierMatSellService {
	public void saveOrUpdateSupplierMatSell(Supplier supplier);
	
	/**
	 * 
	 *〈简述〉根据供应商Id查询销售型
	 *〈详细描述〉
	 * @author myc
	 * @param supplierId 供应商Id
	 * @return
	 */
	public SupplierMatSell getMatSell(String supplierId);

    public String getMatSellIdBySupplierId(String supplierId);
    
    public SupplierMatSell init();
}
