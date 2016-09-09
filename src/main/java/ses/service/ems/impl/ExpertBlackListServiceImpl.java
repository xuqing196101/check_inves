package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertBlackListMapper;
import ses.model.ems.ExpertBlackList;
import ses.service.ems.ExpertBlackListService;

@Service("expertBlackListService")
public class ExpertBlackListServiceImpl implements ExpertBlackListService{
	@Autowired
	private ExpertBlackListMapper mapper;
	
	
	@Override
	public void insert(ExpertBlackList expertBlackList) {
		mapper.insertSelective(expertBlackList);
		
	}

	@Override
	public void update(ExpertBlackList expertBlackList) {
		mapper.updateByPrimaryKeySelective(expertBlackList);
		
	}

	@Override
	public List<ExpertBlackList> findAll() {
		
		return mapper.findList();
	}

	@Override
	public ExpertBlackList findById(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}

}
