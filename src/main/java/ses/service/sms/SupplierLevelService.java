package ses.service.sms;

import java.util.List;

import ses.model.sms.Supplier;

public interface SupplierLevelService {

	public List<Supplier> findSupplier(Supplier supplier, int page);
	
	public void updateScore(Supplier supplier, String scores);
}
