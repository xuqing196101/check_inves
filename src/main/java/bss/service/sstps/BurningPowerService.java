package bss.service.sstps;

import java.util.List;

import bss.model.sstps.BurningPower;

public interface BurningPowerService {
	
	public void insert(BurningPower record);

	public List< BurningPower> selectProduct(BurningPower  record);
	
	public  BurningPower selectById(String id);
	
	public void update(BurningPower record);
	
	public void delete(String id);

}
