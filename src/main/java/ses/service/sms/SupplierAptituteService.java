package ses.service.sms;

import java.util.List;

import ses.model.sms.SupplierAptitute;

public interface SupplierAptituteService {
	public void saveOrUpdateAptitute(SupplierAptitute supplierAptitute);

	public void deleteAptitute(String aptituteIds);
	
	public SupplierAptitute queryById(String id);
	
	List<SupplierAptitute> queryByAptitute(String projectId);
}
