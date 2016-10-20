package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.WagesPayable;

public interface WagesPayableMapper {
	
    int delete(String id);

    int insert(WagesPayable record);

    WagesPayable selectByPrimaryKey(String id);

    int update(WagesPayable record);
    
    List<WagesPayable> selectProduct(WagesPayable record);

}