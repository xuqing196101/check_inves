package yggc.dao.oms;

import java.util.HashMap;

import yggc.model.oms.Deparent;

public interface DeparentMapper {
	Deparent findDeparentByMap(HashMap<String, Object> map);
	int saveDepartment(HashMap<String, Object> map);
}