package ses.service.sms;

import ses.model.sms.SupplierRegPerson;

public interface SupplierRegPersonService {
	public void saveOrUpdateRegPerson(SupplierRegPerson supplierRegPerson);

	public void deleteRegPerson(String regPersonIds);
	
	public SupplierRegPerson queryById(String id);
}
