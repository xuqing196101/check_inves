package bss.service.sstps;

import java.util.List;

import bss.model.sstps.ComCostSummary;

public interface ComCostSummaryService {
	
	public List<ComCostSummary> selectProduct(ComCostSummary comCostSummary);
	
	public void insert(ComCostSummary comCostSummary);
	
	public void update(ComCostSummary comCostSummary);

}
