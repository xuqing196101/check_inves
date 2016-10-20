package bss.service.sstps;

import java.util.List;

import bss.model.sstps.ManufacturingCost;

public interface ManufacturingCostService {
	
	public void insert(ManufacturingCost manufacturingCost);

	public List<ManufacturingCost> selectProduct(ManufacturingCost manufacturingCost);
	
	public ManufacturingCost selectById(String id);
	
	public void update(ManufacturingCost manufacturingCost);
	
	public void delete(String id);

}
