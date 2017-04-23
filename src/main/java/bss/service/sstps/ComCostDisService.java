package bss.service.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.ComCostDis;
import bss.model.sstps.ContractProduct;

public interface ComCostDisService {
	
	public List<ComCostDis> selectProduct(ComCostDis comCostDis);
	
	public void insert(ComCostDis comCostDis);
	
	public void update(ComCostDis comCostDis);
	List<ComCostDis> selectProductIdAndName(HashMap<String, Object> hashMap);
	
	
	public void appendSumComCostDis(ContractProduct contractProduct);

}
