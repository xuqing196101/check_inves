package ses.dao.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ses.model.oms.Orgnization;
public interface OrgnizationMapper {
	int saveOrgnization(HashMap<String, Object> map);
	List<Orgnization> findOrgnizationList(HashMap<String, Object> map);
	int updateOrgnization(HashMap<String, Object> map);
	List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map);
	int delOrgnizationByid(HashMap<String, Object> map);
	int updateOrgnizationById(Orgnization orgnization);

	List<Orgnization> findByName(Map<String, Object> map);
	
}