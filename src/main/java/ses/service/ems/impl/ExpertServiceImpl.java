package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.service.ems.ExpertService;
import ses.util.PropertiesUtil;


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
	public List<Expert> selectAllExpert(Integer pageNum,Expert expert) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		Map map = new HashMap();
		if(expert!=null){
		map.put("relName", expert.getRelName());
		map.put("expertsFrom", expert.getExpertsFrom());
		map.put("status", expert.getStatus());
		map.put("expertsTypeId", expert.getExpertsTypeId());
		}else{
			map.put("relName", null);
			map.put("expertsFrom", null);
			map.put("status", null);
			map.put("expertsTypeId", null);
		}
		return mapper.selectAllExpert(map);
	}

}
