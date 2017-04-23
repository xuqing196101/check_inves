package bss.service.sstps;

import java.util.List;

import bss.model.sstps.ComprehensiveCost;
import bss.model.sstps.ContractProduct;

public interface ComprehensiveCostService {
	
	public void insert(ComprehensiveCost comprehensiveCost);
	
	public void update(ComprehensiveCost comprehensiveCost);
	
	public List<ComprehensiveCost> select(ComprehensiveCost comprehensiveCost);
	
	public void updateInfo(ComprehensiveCost comprehensiveCost);
	
	public void appendSumComprehensiveCost(ContractProduct contractProduct);

}
