package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.BurningPowerMapper;
import bss.model.sstps.BurningPower;
import bss.service.sstps.BurningPowerService;

@Service("/burningPowerService")
public class BurningPowerServiceImpl implements BurningPowerService {
	
	@Autowired
	private BurningPowerMapper burningPowerMapper;

	@Override
	public void insert(BurningPower record) {
		burningPowerMapper.insert(record);
	}

	@Override
	public List<BurningPower> selectProduct(BurningPower record) {
		return burningPowerMapper.selectProduct(record);
	}

	@Override
	public BurningPower selectById(String id) {
		return burningPowerMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(BurningPower record) {
		burningPowerMapper.update(record);
	}

	@Override
	public void delete(String id) {
		burningPowerMapper.delete(id);
	}

}
