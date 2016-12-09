package bss.service.cs.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.PercentFormatter;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.cs.PerformanceMapper;
import bss.model.cs.Performance;
import bss.service.cs.PerformanceService;

@Service("performanceService")
public class PerformanceServiceImpl implements PerformanceService {
	
	@Autowired
	private PerformanceMapper performanceMapper;
	
	@Override
	public void insertSelective(Performance performance) {
		performanceMapper.insertSelective(performance);
	}

	@Override
	public List<Performance> selectAllByidArray(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return performanceMapper.selectAllByidArray(map);
	}

	@Override
	public List<Performance> selectAll(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return performanceMapper.selectAll(map);
	}

	@Override
	public Performance selectByPrimaryKey(String id) {
		return performanceMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateSelective(Performance performance) {
		performanceMapper.updateByPrimaryKeySelective(performance);
	}

	@Override
	public void deleteByPrimaryKey(String id) {
		performanceMapper.deleteByPrimaryKey(id);
	}
}
