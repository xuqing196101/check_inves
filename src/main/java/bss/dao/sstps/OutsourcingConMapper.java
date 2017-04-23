package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.OutsourcingCon;

public interface OutsourcingConMapper {
    int delete(String id);

    int insert(OutsourcingCon record);

    OutsourcingCon selectByPrimaryKey(String id);

    int update(OutsourcingCon record);
    
    List<OutsourcingCon> selectProduct(OutsourcingCon record);
    List<OutsourcingCon> selectProductIdSum(String id);
}