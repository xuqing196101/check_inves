package bss.dao.sstps;

import java.util.HashMap;
import java.util.List;

import bss.model.sstps.ContractProduct;

public interface ContractProductMapper {

    int insert(ContractProduct record);

    ContractProduct selectByPrimaryKey(String id);
    
    List<ContractProduct> select(HashMap<String, Object> map);
    
    int update(ContractProduct record);
    
    List<ContractProduct> selectList(ContractProduct record);

}