package ses.service.ems.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpertFinalInspectMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertFinalInspect;
import ses.service.ems.ExpertFinalInspectService;
import ses.util.PropertiesUtil;

@Service("ExpertFinalInspectService")
public class ExpertFinalInspectServiceImpl implements ExpertFinalInspectService {
	@Autowired
	private ExpertMapper expertMapper;
	@Autowired
	private ExpertFinalInspectMapper mapper;
	//将专家添加至复查列表
	public void addFinalInspect(String expertId,String orgId) {
		Expert expert = expertMapper.selectByPrimaryKey(expertId);
		if(expert.getFinalInspectStatus()==null){
			expert.setFinalInspectPeople(expert.getFinalInspectPeople()+","+orgId);
			if(expert.getFinalInspectCount()==null){
				expert.setFinalInspectCount("0");
			}
		}
		expertMapper.updateExpertFinalInspect(expert);
	}
	//查询复查列表
	public List<Expert> findExpertFinalInspectList(Expert expert,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		if(pageNum != null){
			PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		}
		List<Expert> list = expertMapper.selectAllExpert(expert);
		return list;
	}
	@Override
	public List<ExpertFinalInspect> findExpertFinalInspectList(ExpertFinalInspect e) {
		// TODO Auto-generated method stub
		return mapper.findExpertFinalInspectList(e);
	}
	@Override
	public ExpertFinalInspect getExpertFinalInspect(ExpertFinalInspect e) {
		// TODO Auto-generated method stub
		return mapper.getExpertFinalInspect(e);
	}
	@Override
	public void insertExpertFinalInspect(ExpertFinalInspect e) {
		// TODO Auto-generated method stub
		mapper.insertExpertFinalInspect(e);
	}
	@Override
	public void updateExpertFinalInspect(ExpertFinalInspect e) {
		// TODO Auto-generated method stub
		mapper.updateExpertFinalInspect(e);
	}
	@Override
	public void updateExpertFinalInspectStatusIsNull(ExpertFinalInspect e) {
		// TODO Auto-generated method stub
		mapper.updateExpertFinalInspectStatusIsNull(e);
	}
}
