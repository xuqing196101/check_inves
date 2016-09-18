package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.Orgnization;
public interface OrgnizationMapper {
	int saveOrgnization(HashMap<String, Object> map);
	List<Orgnization> findOrgnizationList(HashMap<String, Object> map);
	int updateOrgnization(HashMap<String, Object> map);
	
}