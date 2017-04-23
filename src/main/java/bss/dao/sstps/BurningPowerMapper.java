package bss.dao.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.BurningPower;

public interface BurningPowerMapper {
	
    int delete(String id);

    int insert(BurningPower record);

    BurningPower selectByPrimaryKey(String id);

    int update(BurningPower record);
    
    List<BurningPower> selectProduct(BurningPower record);
    List<BurningPower> selectProductIdAndName(HashMap<String, Object> map);
}