package ses.service.oms;

import java.util.HashMap;

import ses.model.oms.Deparent;


public interface DepartmentServiceI {
	Deparent findDeparentByMap(HashMap<String, Object> map);
	int saveDepartment(HashMap<String, Object> map);
}
