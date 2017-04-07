package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertEngHistoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertTitle;
import ses.model.sms.SupplierHistory;
import ses.service.ems.ExpertEngHistorySerivce;
import ses.service.ems.ExpertTitleService;
import ses.util.DictionaryDataUtil;

@Service("expertEngHistorySerivce")
public class ExpertEngHistorySerivceImpl implements ExpertEngHistorySerivce{

	@Autowired
	private ExpertEngHistoryMapper expertEngHistoryMapper;
	
	@Autowired
	private ExpertTitleMapper expertTitleMapper;
	
	@Autowired
	private ExpertMapper mapper;
	
	@Override
	public void insertSelective(ExpertEngHistory expertEngHistory) {
		Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String expertId = expertEngHistory.getExpertId();
        
        List<ExpertTitle> expertTitleList = new ArrayList<>();

		//工程技术
		String engCodeId = DictionaryDataUtil.getId("PROJECT");
		//工程经济
		String goodsProjectId = DictionaryDataUtil.getId("GOODS_PROJECT");
		
		Expert expert = mapper.selectByPrimaryKey(expertId);
		if(expert.getExpertsTypeId().contains(engCodeId)){
			expertTitleList = expertTitleMapper.queryByExpertId(expertId,engCodeId);
		}
		if(expert.getExpertsTypeId().contains(goodsProjectId)){
			expertTitleList = expertTitleMapper.queryByExpertId(expertId,goodsProjectId);	
		}
        
		if(!expertTitleList.isEmpty()){
			for(ExpertTitle e : expertTitleList){
				String professional = e.getQualifcationTitle();
				Date timeProfessional = e.getTitleTime();
				expertEngHistory.setCreatedAt(date);
				expertEngHistory.setExpertId(expertId);
				expertEngHistory.setRelationId(e.getId());
				//插入历史表
				expertEngHistory.setContent(professional);
				expertEngHistory.setField("qualifcationTitle");
				expertEngHistoryMapper.insertSelective(expertEngHistory);

				//插入历史表
				expertEngHistory.setContent(format.format(timeProfessional));
				expertEngHistory.setField("titleTime");
				expertEngHistoryMapper.insertSelective(expertEngHistory);
				
			}
		}
	}

	@Override
	public List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory) {
		return expertEngHistoryMapper.selectByExpertId(expertEngHistory);
	}

	@Override
	public void deleteByExpertId(ExpertEngHistory expertEngHistory) {
		expertEngHistoryMapper.deleteByExpertId(expertEngHistory);
		
	}
	
}
