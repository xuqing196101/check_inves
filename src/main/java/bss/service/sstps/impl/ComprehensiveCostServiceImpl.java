package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ComprehensiveCostMapper;
import bss.model.sstps.ComprehensiveCost;
import bss.service.sstps.ComprehensiveCostService;

@Service("/comprehensiveCostService")
public class ComprehensiveCostServiceImpl implements ComprehensiveCostService {

	@Autowired
	private ComprehensiveCostMapper comprehensiveCostMapper;
	
	@Override
	public void insert(ComprehensiveCost comprehensiveCost) {
		// TODO Auto-generated method stub
		comprehensiveCostMapper.insert(comprehensiveCost);
	}

	@Override
	public void update(ComprehensiveCost comprehensiveCost) {
		// TODO Auto-generated method stub
		comprehensiveCostMapper.update(comprehensiveCost);
	}

	@Override
	public List<ComprehensiveCost> select(ComprehensiveCost comprehensiveCost) {
		// TODO Auto-generated method stub
		return comprehensiveCostMapper.selectProduct(comprehensiveCost);
	}

}
