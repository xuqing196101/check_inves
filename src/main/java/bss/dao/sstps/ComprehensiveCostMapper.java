package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.ComprehensiveCost;

public interface ComprehensiveCostMapper {
	
    int delete(String id);

    int insert(ComprehensiveCost record);

    ComprehensiveCost select(String id);

    int update(ComprehensiveCost record);
    
    List<ComprehensiveCost> selectProduct(ComprehensiveCost record);
    
    int updateInfo(ComprehensiveCost record);

}