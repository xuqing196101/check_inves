package ses.dao.oms;

import java.util.HashMap;

import ses.model.oms.Deparent;


public interface DeparentMapper {
	Deparent findDeparentByMap(HashMap<String, Object> map);
	int saveDepartment(HashMap<String, Object> map);
}