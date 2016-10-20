package bss.service.sstps;

import java.util.List;

import bss.model.sstps.PeriodCost;

public interface PeriodCostService {
	
	public void insert(PeriodCost periodCost);

	public List<PeriodCost> selectProduct(PeriodCost periodCost);
	
	public PeriodCost selectById(String id);
	
	public void update(PeriodCost periodCost);
	
	public void delete(String id);


}
