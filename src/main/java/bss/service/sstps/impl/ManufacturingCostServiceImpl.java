package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ManufacturingCostMapper;
import bss.model.sstps.ManufacturingCost;
import bss.service.sstps.ManufacturingCostService;

@Service("/manufacturingCost")
public class ManufacturingCostServiceImpl implements ManufacturingCostService {
	
	@Autowired
	private ManufacturingCostMapper manufacturingCostMapper;

	@Override
	public void insert(ManufacturingCost manufacturingCost) {
		manufacturingCostMapper.insert(manufacturingCost);
	}

	@Override
	public List<ManufacturingCost> selectProduct(
			ManufacturingCost manufacturingCost) {
		return manufacturingCostMapper.selectProduct(manufacturingCost);
	}

	@Override
	public ManufacturingCost selectById(String id) {
		return manufacturingCostMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(ManufacturingCost manufacturingCost) {
		manufacturingCostMapper.update(manufacturingCost);
	}

	@Override
	public void delete(String id) {
		manufacturingCostMapper.delete(id);
	}

}
