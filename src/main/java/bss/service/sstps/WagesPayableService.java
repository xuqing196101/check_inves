package bss.service.sstps;

import java.util.List;

import bss.model.sstps.WagesPayable;

public interface WagesPayableService {

	public void insert(WagesPayable wagesPayable);

	public List<WagesPayable> selectProduct(WagesPayable  wagesPayable);
	
	public WagesPayable selectById(String id);
	
	public void update(WagesPayable wagesPayable);
	
	public void delete(String id);

}
