package yggc.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.ems.ExpertMapper;
import yggc.model.ems.Expert;
import yggc.service.ems.ExpertService;

@Service("expertService")
public class ExpertServiceImpl implements ExpertService {

	@Autowired
	private ExpertMapper mapper;
	
	@Override
	public void deleteByPrimaryKey(String id) {
		mapper.deleteByPrimaryKey(id);

	}

	@Override
	public int insertSelective(Expert record) {
		
		return mapper.insertSelective(record);
	}

	@Override
	public Expert selectByPrimaryKey(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(Expert record) {
		mapper.updateByPrimaryKeySelective(record);

	}

	@Override
	public List<Expert> selectLoginNameList(String loginName) {
		List<Expert> expertList = mapper.selectLoginNameList(loginName);
		return expertList;
	}

	@Override
	public List<Expert> selectAllExpert() {
		
		return mapper.selectAllExpert();
	}

}
