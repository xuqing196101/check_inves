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
import ses.service.ems.ExpertEngHistorySerivce;
import ses.util.DictionaryDataUtil;

/**
 * <p>Title:ExpertEngHistoryMapper </p>
 * <p>Description: 工程下的职业资格历史记录</p>
 * @date 2017-5-2下午4:29:48
 */
@Service("expertEngHistorySerivce")
public class ExpertEngHistorySerivceImpl implements ExpertEngHistorySerivce{

	@Autowired
	private ExpertEngHistoryMapper expertEngHistoryMapper;
	
	@Autowired
	private ExpertTitleMapper expertTitleMapper;
	
	@Autowired
	private ExpertMapper mapper;
	
	/**
	 * 插入历史数据
	 */
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
        
		if(!expertTitleList.isEmpty() && expertTitleList.size() > 0){
			for(ExpertTitle e : expertTitleList){
				if(e !=null ){
					expertEngHistory.setCreatedAt(date);
					expertEngHistory.setExpertId(expertId);
					expertEngHistory.setRelationId(e.getId());
					//插入历史表
					if(e.getQualifcationTitle() !=null){
						expertEngHistory.setContent(e.getQualifcationTitle());
						expertEngHistory.setField("qualifcationTitle");
						expertEngHistoryMapper.insertSelective(expertEngHistory);
					}
					
					if(e.getTitleTime() !=null){
						//插入历史表
						expertEngHistory.setContent(format.format(e.getTitleTime()));
						expertEngHistory.setField("titleTime");
						expertEngHistoryMapper.insertSelective(expertEngHistory);
					}
				}
			}
		}
	}
	
	/**
	 * 查询历史数据
	 */
	@Override
	public List<ExpertEngHistory> selectByExpertId(ExpertEngHistory expertEngHistory) {
		return expertEngHistoryMapper.selectByExpertId(expertEngHistory);
	}

	@Override
	public void deleteByExpertId(ExpertEngHistory expertEngHistory) {
		expertEngHistoryMapper.deleteByExpertId(expertEngHistory);
		
	}

	/**
	 * @Title: updateIsDeletedByExpertId
	 * @author XuQing 
	 * @date 2017-5-2 下午4:31:09  
	 * @Description:软删除历史数据
	 * @param @param expertId      
	 * @return void
	 */
	@Override
	public void updateIsDeletedByExpertId(ExpertEngHistory expertEngHistory){
		expertEngHistoryMapper.updateIsDeletedByExpertId(expertEngHistory);
		
	}
	
}
