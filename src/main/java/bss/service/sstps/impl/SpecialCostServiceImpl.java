package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.SpecialCostMapper;
import bss.model.sstps.SpecialCost;
import bss.service.sstps.SpecialCostService;

@Service("/specialCostService")
public class SpecialCostServiceImpl implements SpecialCostService {

	@Autowired
	private SpecialCostMapper specialCostMapper;
	
	@Override
	public void insert(SpecialCost specialCost) {
		specialCostMapper.insert(specialCost);
	}

	@Override
	public List<SpecialCost> selectProduct(SpecialCost specialCost) {
		return specialCostMapper.selectProduct(specialCost);
	}

	@Override
	public SpecialCost selectById(String id) {
		return specialCostMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(SpecialCost specialCost) {
		specialCostMapper.update(specialCost);
	}

	@Override
	public void delete(String id) {
		specialCostMapper.delete(id);
	}

	@Override
	public List<SpecialCost> selectProjectNameByProId(String proId) {
		return specialCostMapper.selectProjectNameByProId(proId);
	}

	@Override
	public List<SpecialCost> selectByIdAndParentId(String id) {
		return specialCostMapper.selectByIdAndParentId(id);
	}

}
