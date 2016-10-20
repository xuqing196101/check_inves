package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.ComCostDisMapper;
import bss.model.sstps.ComCostDis;
import bss.service.sstps.ComCostDisService;

@Service("/comCostDisService")
public class ComCostDisServiceImpl implements ComCostDisService {

	@Autowired
	private ComCostDisMapper comCostDisMapper;
	
	@Override
	public List<ComCostDis> selectProduct(ComCostDis comCostDis) {
		return comCostDisMapper.selectProduct(comCostDis);
	}

	@Override
	public void insert(ComCostDis comCostDis) {
		comCostDisMapper.insert(comCostDis);
	}

	@Override
	public void update(ComCostDis comCostDis) {
		comCostDisMapper.update(comCostDis);
	}

}
