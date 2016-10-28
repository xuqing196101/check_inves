package bss.service.prms.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.ReviewProgressMapper;
import bss.model.prms.ReviewProgress;
import bss.service.prms.ReviewProgressService;
import ses.util.WfUtil;
@Service("reviewProgressService")
public class ReviewProgressServiceImpl implements ReviewProgressService {

	@Autowired
	private ReviewProgressMapper mapper;
	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int save(ReviewProgress record) {
		// TODO Auto-generated method stub
		record.setId(WfUtil.createUUID());
		return mapper.insert(record);
	}

	@Override
	public ReviewProgress getById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ReviewProgress record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ReviewProgress record) {
		// TODO Auto-generated method stub
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void updateByMap(ReviewProgress record) {
		// TODO Auto-generated method stub
		mapper.updateByMap(record);

	}

	@Override
	public List<ReviewProgress> selectByMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		return mapper.selectByMap(map);
	}

}
