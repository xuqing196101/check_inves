package bss.service.sstps;

import java.util.List;

import bss.model.sstps.SpecialCost;

public interface SpecialCostService {
	
	public void insert(SpecialCost specialCost);

	public List< SpecialCost> selectProduct(SpecialCost  specialCost);
	
	public  SpecialCost selectById(String id);
	
	public void update(SpecialCost specialCost);
	
	public void delete(String id);
	List<SpecialCost> selectProjectNameByProId(String proId);
	
	List<SpecialCost> selectByIdAndParentId(String id);
}
