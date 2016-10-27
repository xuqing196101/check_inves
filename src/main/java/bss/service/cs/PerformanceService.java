package bss.service.cs;

import java.util.List;
import java.util.Map;

import bss.model.cs.Performance;

public interface PerformanceService {
	void insertSelective(Performance performance);
	
	List<Performance> selectAllByidArray(Map<String, Object> map);
	
	List<Performance> selectAll(Map<String, Object> map);
	
	Performance selectByPrimaryKey(String id);
	
	void updateSelective(Performance performance);
}
