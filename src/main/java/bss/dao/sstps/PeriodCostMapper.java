package bss.dao.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.PeriodCost;

public interface PeriodCostMapper {
	
    int delete(String id);

    int insert(PeriodCost record);

    PeriodCost selectByPrimaryKey(String id);

    int update(PeriodCost record);
    
    List<PeriodCost> selectProduct(PeriodCost record);
    List<PeriodCost> selectProductIdAndName(HashMap<String, Object> hashMap);
    
}