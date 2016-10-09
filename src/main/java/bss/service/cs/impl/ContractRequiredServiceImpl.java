package bss.service.cs.impl;

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
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(ContractRequired record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(ContractRequired record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
