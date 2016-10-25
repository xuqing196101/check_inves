package bss.service.iacs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.iacs.ForeignContractMapper;
import bss.model.iacs.ForeignContract;
import bss.service.iacs.ForeignContractService;
/**
 * 
 * @Title: ForeignContractServiceImpl
 * @Description: 外贸合同业务实现类 
 * @author Li Xiaoxiao
 * @date  2016年10月25日,下午4:30:15
 *
 */
@Service
public class ForeignContractServiceImpl implements ForeignContractService {

	
	@Autowired
	private ForeignContractMapper foreignContractMapper;
	
	public void add(ForeignContract foreignContract) {
	 
		foreignContractMapper.insertSelective(foreignContract);
	}

	@Override
	public void update(ForeignContract foreignContract) {
		// TODO Auto-generated method stub
		foreignContractMapper.updateByPrimaryKeySelective(foreignContract);
	}

	@Override
	public ForeignContract queryById(String id) {
		// TODO Auto-generated method stub
		return foreignContractMapper.selectByPrimaryKey(id);
	}

}
