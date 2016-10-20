package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.PeriodCostMapper;
import bss.model.sstps.PeriodCost;
import bss.service.sstps.PeriodCostService;

@Service("/periodCostService")
public class PeriodCostServiceImpl implements PeriodCostService {

	@Autowired
	private PeriodCostMapper periodCostMapper;
	
	@Override
	public void insert(PeriodCost periodCost) {
		periodCostMapper.insert(periodCost);
	}

	@Override
	public List<PeriodCost> selectProduct(PeriodCost periodCost) {
		return periodCostMapper.selectProduct(periodCost);
	}

	@Override
	public PeriodCost selectById(String id) {
		return periodCostMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(PeriodCost periodCost) {
		periodCostMapper.update(periodCost);
	}

	@Override
	public void delete(String id) {
		periodCostMapper.delete(id);
	}

}
