package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.Orgnization;



public interface OrgniztionMapper {
	public List<Orgnization> findOrgnizationList(HashMap<String,Object> map);
}