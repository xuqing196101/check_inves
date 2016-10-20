package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.ComCostSummary;

public interface ComCostSummaryMapper {
	
    int delete(String id);

    int insert(ComCostSummary record);

    ComCostSummary selectByPrimaryKey(String id);

    int update(ComCostSummary record);
    
    List<ComCostSummary> selectProduct(ComCostSummary record);

}