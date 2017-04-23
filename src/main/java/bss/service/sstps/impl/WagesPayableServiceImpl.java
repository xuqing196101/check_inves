package bss.service.sstps.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.WagesPayableMapper;
import bss.model.sstps.WagesPayable;
import bss.service.sstps.WagesPayableService;

@Service("/wagesPayableService")
public class WagesPayableServiceImpl implements WagesPayableService {

	@Autowired
	private WagesPayableMapper wagesPayableMapper;
	
	@Override
	public void insert(WagesPayable wagesPayable) {
		wagesPayableMapper.insert(wagesPayable);
	}

	@Override
	public List<WagesPayable> selectProduct(WagesPayable wagesPayable) {
		return wagesPayableMapper.selectProduct(wagesPayable);
	}

	@Override
	public WagesPayable selectById(String id) {
		return wagesPayableMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(WagesPayable wagesPayable) {
		wagesPayableMapper.update(wagesPayable);
	}

	@Override
	public void delete(String id) {
		wagesPayableMapper.delete(id);
	}

	@Override
	public List<WagesPayable> selectProductIdName(
			HashMap<String, Object> hashMap) {
		return wagesPayableMapper.selectProductIdName(hashMap);
	}

}
