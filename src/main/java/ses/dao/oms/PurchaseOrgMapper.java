package ses.dao.oms;

import java.util.HashMap;

public interface PurchaseOrgMapper {
	int  saveByMap(HashMap<String, Object> map);
	int delByMap(HashMap<String, Object> map);
	int delByOrgId(HashMap<String, Object> map);
}