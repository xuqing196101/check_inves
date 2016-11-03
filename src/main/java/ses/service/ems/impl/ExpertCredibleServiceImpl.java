package ses.service.ems.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpertCredibleMapper;
import ses.model.ems.ExpertCredible;
import ses.service.ems.ExpertCredibleService;
import ses.util.PropertiesUtil;
import ses.util.WfUtil;
@Service("ExpertCredibleService")
public class ExpertCredibleServiceImpl implements ExpertCredibleService {

	@Autowired
	private ExpertCredibleMapper mapper;
	
	@Override
	public int deleteById(String id) {
		// TODO Auto-generated method stub
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int save(ExpertCredible record) {
		// TODO Auto-generated method stub
		record.setId(WfUtil.createUUID());
		record.setCreateAt(new Date());
		record.setIsDelete((short) 1);
		return mapper.insert(record);
	}

	@Override
	public ExpertCredible findById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateById(ExpertCredible record) {
		// TODO Auto-generated method stub
		record.setUpdateAt(new Date());
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<ExpertCredible> list(Integer pageNum, Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return mapper.selectAll(map);
	}

	@Override
	public List<ExpertCredible> findAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return mapper.selectAll(map);
	}

}
