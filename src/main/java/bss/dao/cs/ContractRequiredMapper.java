package bss.dao.cs;

import java.util.List;
import java.util.Map;

import bss.model.cs.ContractRequired;

public interface ContractRequiredMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContractRequired record);

    int insertSelective(ContractRequired record);

    ContractRequired selectConRequByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContractRequired record);

    int updateByPrimaryKey(ContractRequired record);
    
    List<ContractRequired> selectConRequeByContractId(String conId);
    
    void deleteByContractId(String id);
    
    /**
     * @Title: findByMap
     * @author: Wang Zhaohua
     * @date: 2016-11-10 下午8:31:18
     * @Description: findByMap
     * @param: @param param
     * @param: @return
     * @return: List<ContractRequired>
     */
    List<ContractRequired> findByMap(Map<String, Object> param);
}