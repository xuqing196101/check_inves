package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.YearPlan;

public interface YearPlanMapper {
	
    int delete(String id);

    int insert(YearPlan record);

    YearPlan selectByPrimaryKey(String id);

    int update(YearPlan record);
    
    List<YearPlan> selectProduct(YearPlan record);
    List<YearPlan> selectByParentIdZero(String record);

}