package ses.service.ems.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertTitleMapper;
import ses.model.ems.ExpertTitle;
import ses.service.ems.ExpertTitleService;

@Service
public class ExpertTitleServiceImpl implements ExpertTitleService{

	@Autowired
	private ExpertTitleMapper  expertTitleMapper;
	
	@Override
	public List<ExpertTitle> getAll(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(ExpertTitle expertTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ExpertTitle expertTitle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ExpertTitle> queryByUserId(String expertId) {
		 
		return expertTitleMapper.queryByExpertId(expertId);
	}

	@Override
	public void addBatch(List<ExpertTitle> list) {
		 for(ExpertTitle ep:list){
			 ExpertTitle expertTitle = expertTitleMapper.selectByPrimaryKey(ep.getId());
			 if(expertTitle==null&&ep.getQualifcationTitle()!=null){
				 ep.setCreatedAt(new Date());
				 ep.setStatus("1");
				 expertTitleMapper.insertSelective(ep);
			 }else if(expertTitle!=null){
				 expertTitleMapper.updateByPrimaryKeySelective(ep);
			 }
			
		 }
		
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		expertTitleMapper.deleteById(id);
	}

	

}
