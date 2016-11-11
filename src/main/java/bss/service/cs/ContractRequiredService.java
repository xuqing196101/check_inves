package bss.service.cs;

import java.util.List;

import bss.model.cs.ContractRequired;

public interface ContractRequiredService {
	int deleteByPrimaryKey(String id);

    int insert(ContractRequired record);

    int insertSelective(ContractRequired record);

    ContractRequired selectConRequByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractRequired record);

    int updateByPrimaryKey(ContractRequired record);
    
    List<ContractRequired> selectConRequeByContractId(String conId);
    
    void deleteByContractId(String id);
    
    /**
     * @Title: findContractRequiredByConId
     * @author: Wang Zhaohua
     * @date: 2016-11-11 上午9:50:19
     * @Description: 根据合同 ids 找到合同明细
     * @param: @param ids
     * @param: @return
     * @return: List<ContractRequired>
     */
    List<ContractRequired> findContractRequiredByConId(String ids);
}
