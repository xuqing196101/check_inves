package bss.service.sstps;

import java.util.List;

import bss.model.sstps.ComCostDis;

public interface ComCostDisService {
	
	public List<ComCostDis> selectProduct(ComCostDis comCostDis);
	
	public void insert(ComCostDis comCostDis);
	
	public void update(ComCostDis comCostDis);

}
