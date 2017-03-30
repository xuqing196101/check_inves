package bss.service.cs.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.cs.ContractRequiredMapper;
import bss.model.cs.ContractRequired;
import bss.service.cs.ContractRequiredService;

@Service("contractRequiredService")
public class ContractRequiredServiceImpl implements ContractRequiredService {
	
	@Autowired
	private ContractRequiredMapper contractRequiredMapper;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ContractRequired record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(ContractRequired record) {
		contractRequiredMapper.insertSelective(record);
		return 0;
	}

	@Override
	public ContractRequired selectConRequByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return contractRequiredMapper.selectConRequByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ContractRequired record) {
		contractRequiredMapper.updateByPrimaryKeySelective(record);
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ContractRequired record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ContractRequired> selectConRequeByContractId(String conId) {
		return contractRequiredMapper.selectConRequeByContractId(conId);
	}

	@Override
	public void deleteByContractId(String id) {
		contractRequiredMapper.deleteByContractId(id);
	}
	
	/**
     * @Title: findContractRequiredByConId
     * @author: Wang Zhaohua
     * @date: 2016-11-11 上午9:50:19
     * @Description: 根据合同 ids 找到合同明细
     * @param: @param ids
     * @param: @return
     * @return: List<ContractRequired>
     */
	@Override
	public List<ContractRequired> findContractRequiredByConId(String ids) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids.split(","));
		return contractRequiredMapper.findByMap(param);
	}

	@Override
	public List<ContractRequired> selectConRequByDetailId(String id) {
		return contractRequiredMapper.selectConRequByDetailId(id);
	}
}
