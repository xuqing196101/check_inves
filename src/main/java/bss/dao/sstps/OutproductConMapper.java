package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.OutproductCon;

public interface OutproductConMapper {
	
    int delete(String id);

    int insert(OutproductCon record);

    OutproductCon selectByPrimaryKey(String id);

    int update(OutproductCon record);
    
    List<OutproductCon> selectProduct(OutproductCon record);
    List<OutproductCon> selectProductIdSum(String id);

}