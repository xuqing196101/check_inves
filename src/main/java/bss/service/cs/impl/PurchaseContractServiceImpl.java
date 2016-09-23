package bss.service.cs.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.cs.PurchaseContractMapper;
import bss.model.cs.PurchaseContract;
import bss.service.cs.PurchaseContractService;

@Service("purchaseContractService")
public class PurchaseContractServiceImpl implements PurchaseContractService {
	
	@Autowired
	private PurchaseContractMapper purchaseContractMapper;
	
	@Override
	public int insert(PurchaseContract record) {
		return 0;
	}

	@Override
	public int insertSelective(PurchaseContract record) {
		return 0;
	}

}
