package ses.service.sms;

import java.util.List;
import java.util.Map;

import common.utils.JdcgResult;

import ses.model.sms.SupplierStars;

public interface SupplierStarsService {
	public List<SupplierStars> findSupplierStars(Map<String, Object> map);
	
	public JdcgResult saveOrUpdateSupplierStars(SupplierStars supplierStars);
	
	public SupplierStars get(String id);
	
	public void updateStatus(SupplierStars supplierStars);
	
	public void delete(String ids);
}
