package ses.dao.oms;

import java.util.HashMap;
import java.util.List;

import ses.model.oms.PurchaseOrg;

public interface PurchaseOrgMapper {
	int  saveByMap(HashMap<String, Object> map);
	int delByMap(HashMap<String, Object> map);
	int delByOrgId(HashMap<String, Object> map);
	List<PurchaseOrg> selectById(HashMap<String, Object> map);
	
	List<PurchaseOrg> selectByOrgId(HashMap<String, Object> map);
}