package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.ContractProduct;

public interface ContractProductMapper {

    int insert(ContractProduct record);

    ContractProduct selectByPrimaryKey(String id);
    
    List<ContractProduct> select(ContractProduct record);

}