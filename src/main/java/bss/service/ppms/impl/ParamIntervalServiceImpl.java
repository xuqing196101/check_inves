package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import bss.dao.ppms.ParamIntervalMapper;
import bss.model.ppms.ParamInterval;
import bss.service.ppms.ParamIntervalService;
@Service("paramIntervalService")
public class ParamIntervalServiceImpl implements ParamIntervalService{
	@Autowired
	private ParamIntervalMapper paramIntervalMapper;

	@Override
	public List<ParamInterval> findListByParamInterval(
			ParamInterval paramInterval) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.findListByParamInterval(paramInterval);
	}

	@Override
	public int saveParamInterval(ParamInterval paramInterval) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.saveParamInterval(paramInterval);
	}

	@Override
	public int updateParamInterval(ParamInterval paramInterval) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.updateParamInterval(paramInterval);
	}

	@Override
	public int delParamIntervalByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.delParamIntervalByid(map);
	}

	@Override
	public int delSoftParamIntervalByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.delSoftParamIntervalByid(map);
	}

	@Override
	public int delParamIntervalByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return paramIntervalMapper.delParamIntervalByMap(map);
	}
}
