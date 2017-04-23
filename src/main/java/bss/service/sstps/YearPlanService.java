package bss.service.sstps;

import java.util.List;

import bss.model.sstps.YearPlan;

public interface YearPlanService {
	
	public void insert(YearPlan yearPlan);

	public List<YearPlan> selectProduct(YearPlan  yearPlan);
	
	public YearPlan selectById(String id);
	
	public void update(YearPlan yearPlan);
	
	public void delete(String id);
	List<YearPlan> selectByParentIdZero(String record);

}
