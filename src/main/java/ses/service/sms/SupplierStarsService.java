package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierStars;

public interface SupplierStarsService {
	public List<SupplierStars> findSupplierStars();
	
	public void saveOrUpdateSupplierStars(SupplierStars supplierStars);
	
	public SupplierStars get(String id);
	
	public void updateStatus(SupplierStars supplierStars);
}
