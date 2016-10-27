package bss.dao.cs;

import java.util.List;
import java.util.Map;

import bss.model.cs.Performance;

public interface PerformanceMapper {
    int deleteByPrimaryKey(String id);

    int insert(Performance record);

    int insertSelective(Performance record);

    Performance selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Performance record);

    int updateByPrimaryKey(Performance record);
    
    List<Performance> selectAllByidArray(Map<String, Object> map);
    
    List<Performance> selectAll();
}