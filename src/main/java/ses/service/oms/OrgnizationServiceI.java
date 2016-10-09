package ses.service.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.Orgnization;


public interface OrgnizationServiceI {
	public List<Orgnization> findOrgnizationList(HashMap<String,Object> map);
	int saveOrgnization(HashMap<String, Object> map);
	int updateOrgnization(HashMap<String, Object> map);
	List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	int delOrgnizationByid(HashMap<String, Object> map);
	int updateOrgnizationById(Orgnization orgnization);
}
