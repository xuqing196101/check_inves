package yggc.service.oms.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.oms.DeparentMapper;
import yggc.model.oms.Deparent;
import yggc.service.oms.DepartmentServiceI;
@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentServiceI{
	@Autowired
	private DeparentMapper deparentMapper;

	@Override
	public Deparent findDeparentByMap(HashMap<String, Object> map) {
		return deparentMapper.findDeparentByMap(map);
	}

	@Override
	public int saveDepartment(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return deparentMapper.saveDepartment(map);
	}

	
	
}
