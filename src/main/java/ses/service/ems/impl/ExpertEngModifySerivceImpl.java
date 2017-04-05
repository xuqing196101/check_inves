package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertEngModifyMapper;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertTitle;
import ses.service.ems.ExpertEngHistorySerivce;
import ses.service.ems.ExpertEngModifySerivce;
import ses.service.ems.ExpertTitleService;

@Service("expertEngModifySerivce")
public class ExpertEngModifySerivceImpl implements ExpertEngModifySerivce{

	@Autowired
	private ExpertEngModifyMapper expertEngModifyMapper;
	
	@Autowired
	private ExpertEngHistorySerivce expertEngHistorySerivce;
	
	@Autowired
	private ExpertTitleService expertTitleService ; 
	
	
	@Override
	public void insertSelective(ExpertEngHistory expertEngHistory) {
		String expertId = expertEngHistory.getExpertId();
		Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//历史
		List<ExpertEngHistory> expertEngHistoryList = expertEngHistorySerivce.selectByExpertId(expertEngHistory);
		//现在
		List<ExpertTitle> expertTitleList = expertTitleService.queryByUserId(expertId);
		//对比
		expertEngHistory.setCreatedAt(date);
		for(ExpertEngHistory history :expertEngHistoryList){
			for(ExpertTitle expertTitle: expertTitleList){
				if(history.getRelationId().equals(expertTitle.getId())){
					
					expertEngHistory.setRelationId(expertTitle.getId());
					//执业资格
					if("professional".equals(history.getContent())){
						if(!history.getContent().equals(expertTitle.getQualifcationTitle())){
							expertEngHistory.setContent(history.getContent());
							//插入数据
							expertEngModifyMapper.insertSelective(expertEngHistory);
						}
					}
					
					//执业资格时间
					if("timeProfessional".equals(history.getContent())){
						String ProfessionalTime = format.format(expertTitle.getTitleTime());
						if(!history.getContent().equals(ProfessionalTime)){
							expertEngHistory.setContent(history.getContent());
							//插入数据
							expertEngModifyMapper.insertSelective(expertEngHistory);
						}
					}
					
				}
				
			}
		}
	}

	@Override
	public List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory) {
		return expertEngModifyMapper.selectByExpertId(expertEngHistory);
	}

	@Override
	public void deleteByExpertId(ExpertEngHistory expertEngHistory) {
		expertEngModifyMapper.deleteByExpertId(expertEngHistory);
		
	}
	
}
