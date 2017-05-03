package ses.service.ems.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertEngModifyMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertEngHistory;
import ses.model.ems.ExpertTitle;
import ses.service.ems.ExpertEngHistorySerivce;
import ses.service.ems.ExpertEngModifySerivce;
import ses.service.ems.ExpertTitleService;
import ses.util.DictionaryDataUtil;

@Service("expertEngModifySerivce")
public class ExpertEngModifySerivceImpl implements ExpertEngModifySerivce{

	@Autowired
	private ExpertEngModifyMapper expertEngModifyMapper;
	
	@Autowired
	private ExpertEngHistorySerivce expertEngHistorySerivce;
	
	@Autowired
	private ExpertTitleMapper expertTitleMapper;
	
	@Autowired
	private ExpertMapper mapper;
	
	
	@Override
	public void insertSelective(ExpertEngHistory expertEngHistory) {
		String expertId = expertEngHistory.getExpertId();
		Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		//历史
		List<ExpertEngHistory> expertEngHistoryList = expertEngHistorySerivce.selectByExpertId(expertEngHistory);
		/**
		 * 现在
		 */
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
	        
		//对比
		expertEngHistory.setCreatedAt(date);
		for(ExpertEngHistory history :expertEngHistoryList){
			for(ExpertTitle expertTitle: expertTitleList){
				if(history.getRelationId().equals(expertTitle.getId())){
					
					expertEngHistory.setRelationId(expertTitle.getId());
					//执业资格
					if("qualifcationTitle".equals(history.getField())){
						if(!history.getContent().equals(expertTitle.getQualifcationTitle())){
							expertEngHistory.setField("qualifcationTitle");
							expertEngHistory.setContent(history.getContent());
							//插入数据
							expertEngModifyMapper.insertSelective(expertEngHistory);
						}
					}
					
					//执业资格时间
					if("titleTime".equals(history.getField())){
						String ProfessionalTime = format.format(expertTitle.getTitleTime());
						if(!history.getContent().equals(ProfessionalTime)){
							expertEngHistory.setField("titleTime");
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
	
	/**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午5:03:13  
	 * @Description:软删除
	 * @param @param expertId      
	 * @return void
	 */
	@Override
	public void updateIsDeletedByExpertId(String expertId) {
		expertEngModifyMapper.updateIsDeletedByExpertId(expertId);
		
	}
	
}
