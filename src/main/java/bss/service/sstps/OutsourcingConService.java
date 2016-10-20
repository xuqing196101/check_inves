package bss.service.sstps;

import java.util.List;

import bss.model.sstps.OutsourcingCon;

public interface OutsourcingConService {
	
	public void insert( OutsourcingCon  outsourcingCon);

	public List< OutsourcingCon> selectProduct( OutsourcingCon  outsourcingCon);
	
	public  OutsourcingCon selectById(String id);
	
	public void update( OutsourcingCon outsourcingCon);
	
	public void delete(String id);

}
