package bss.service.sstps.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.sstps.YearPlanMapper;
import bss.model.sstps.YearPlan;
import bss.service.sstps.YearPlanService;

@Service("/yearPlanService")
public class YearPlanServiceImpl implements YearPlanService {
	
	@Autowired
	private YearPlanMapper yearPlanMapper;

	@Override
	public void insert(YearPlan yearPlan) {
		yearPlanMapper.insert(yearPlan);
	}

	@Override
	public List<YearPlan> selectProduct(YearPlan yearPlan) {
		return yearPlanMapper.selectProduct(yearPlan);
	}

	@Override
	public YearPlan selectById(String id) {
		return yearPlanMapper.selectByPrimaryKey(id);
	}

	@Override
	public void update(YearPlan yearPlan) {
		yearPlanMapper.update(yearPlan);
	}

	@Override
	public void delete(String id) {
		yearPlanMapper.delete(id);
	}

	@Override
	public List<YearPlan> selectByParentIdZero(String record) {
		return yearPlanMapper.selectByParentIdZero(record);
	}

}
