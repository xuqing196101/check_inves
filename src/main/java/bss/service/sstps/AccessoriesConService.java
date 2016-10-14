package bss.service.sstps;

import java.util.List;

import bss.model.sstps.AccessoriesCon;

public interface AccessoriesConService {
	
	public void insert(AccessoriesCon accessoriesCon);

	public List<AccessoriesCon> selectProduct(AccessoriesCon accessoriesCon);
	
	public AccessoriesCon selectById(String id);
	
	public void update(AccessoriesCon accessoriesCon);
	
	public void delete(String id);
	
}
