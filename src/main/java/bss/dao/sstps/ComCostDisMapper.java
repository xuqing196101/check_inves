package bss.dao.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.ComCostDis;

public interface ComCostDisMapper {
	
    int delete(String id);

    int insert(ComCostDis record);

    ComCostDis selectByPrimaryKey(String id);

    int update(ComCostDis record);
    
    List<ComCostDis> selectProduct(ComCostDis record);
    
    List<ComCostDis> selectProductIdAndName(HashMap<String, Object> hashMap);
    
}