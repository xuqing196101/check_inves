package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.ManufacturingCost;

public interface ManufacturingCostMapper {
	
    int delete(String id);

    int insert(ManufacturingCost record);

    ManufacturingCost selectByPrimaryKey(String id);

    int update(ManufacturingCost record);
    
    List<ManufacturingCost> selectProduct(ManufacturingCost record);
    List<ManufacturingCost> selectProductIdSum(String id);
    
}