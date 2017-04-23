package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.AccessoriesCon;

public interface AccessoriesConMapper {
	
    int delete(String id);

    int insert(AccessoriesCon record);

    AccessoriesCon selectByPrimaryKey(String id);

    int update(AccessoriesCon record);
    
    List<AccessoriesCon> selectProduct(AccessoriesCon record);
    List<AccessoriesCon> selectProductIdAndParentId(String id);
    
}