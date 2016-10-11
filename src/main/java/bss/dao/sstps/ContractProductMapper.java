package bss.dao.sstps;

import bss.model.sstps.ContractProduct;

public interface ContractProductMapper {

    int insert(ContractProduct record);

    ContractProduct selectByPrimaryKey(String id);

}