package ses.service.sms;

import java.util.List;
import java.util.Map;

import ses.model.sms.SupplierAptitute;

public interface SupplierAptituteService {
	public void saveOrUpdateAptitute(SupplierAptitute supplierAptitute);

	public void deleteAptitute(String aptituteIds);
	
	public SupplierAptitute queryById(String id);
	
	List<SupplierAptitute> queryByAptitute(String projectId);
	
	public List<SupplierAptitute> queryByCodeAndType(String certType,String matEngId,String code,String type);
	
	public List<String> getPorType(String typeId, String matEngId, String code);

	public int selectByCertCode(String certCode);

	public Map<String, Object> getEngAptitute(String suppId);
}
