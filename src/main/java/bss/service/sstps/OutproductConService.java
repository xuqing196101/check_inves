package bss.service.sstps;

import java.util.List;

import bss.model.sstps.OutproductCon;

public interface OutproductConService {
	
	public void insert(OutproductCon outproductCon);

	public List<OutproductCon> selectProduct(OutproductCon outproductCon);
	
	public OutproductCon selectById(String id);
	
	public void update(OutproductCon outproductCon);
	
	public void delete(String id);

}
