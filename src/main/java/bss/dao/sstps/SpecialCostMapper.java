package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.SpecialCost;

public interface SpecialCostMapper {
	
    int delete(String id);

    int insert(SpecialCost record);

    SpecialCost selectByPrimaryKey(String id);

    int update(SpecialCost record);
    
    List<SpecialCost> selectProduct(SpecialCost record);
    
    List<SpecialCost> selectProjectNameByProId(String proId);
    
    List<SpecialCost> selectByIdAndParentId(String id);
    
}