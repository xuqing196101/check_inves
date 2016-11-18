package bss.service.prms.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.prms.ExpertScoreMapper;
import bss.model.ppms.SupplyMark;
import bss.model.prms.ExpertScore;
import bss.service.prms.ExpertScoreService;
import ses.util.WfUtil;
@Service
public class ExpertScoreServiceImpl implements ExpertScoreService {

	@Autowired
	private ExpertScoreMapper mapper;
	@Override
	public void deleteByPrimaryKey(String id) {
		mapper.deleteByPrimaryKey(id);
	}

	@Override
	public void insert(ExpertScore record) {
		record.setId(WfUtil.createUUID());
		mapper.insert(record);

	}

	@Override
	public void insertSelective(ExpertScore record) {
		record.setId(WfUtil.createUUID());
		mapper.insertSelective(record);

	}

	@Override
	public ExpertScore selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateByPrimaryKeySelective(ExpertScore record) {
		mapper.updateByPrimaryKeySelective(record);

	}

	@Override
	public void updateByPrimaryKey(ExpertScore record) {
		mapper.updateByPrimaryKey(record);

	}

	@Override
	public List<ExpertScore> selectByMap(Map<String, Object> map) {
		return mapper.selectByMap(map);
	}

	@Override
	public void saveScore(ExpertScore expertScore, List<SupplyMark> supplyMarkList,String scoreModelId) {
		Map<String, Object> map = new HashMap<>();
		map.put("expertId", expertScore.getExpertId());
		map.put("projectId", expertScore.getProjectId());
		map.put("packageId", expertScore.getPackageId());
		map.put("scoreModelId", expertScore.getScoreModelId());
		if(supplyMarkList != null && supplyMarkList.size()>0){
		for (SupplyMark supplyMark : supplyMarkList) {
			map.put("supplierId", supplyMark.getSupplierId());
			List<ExpertScore> expertScoreList = mapper.selectByMap(map);
			double prarm = supplyMark.getPrarm();
			double score = supplyMark.getScore();
			if(expertScoreList!= null && expertScoreList.size()>0){
				//不为空则修改以前的为历史记录
				for (ExpertScore expertScore2 : expertScoreList) {
					if(expertScore2.getIsHistory()!=1){
						expertScore2.setIsHistory((short) 1);
						mapper.updateByPrimaryKeySelective(expertScore2);
					}
				}
				//然后在新增一条新的数据
				expertScore.setId(WfUtil.createUUID());
				expertScore.setSupplierId(supplyMark.getSupplierId());
				BigDecimal expertValue = new BigDecimal(prarm);
				BigDecimal score2 = new BigDecimal(score);
				expertScore.setExpertValue(expertValue);
				expertScore.setScore(score2);
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}else{
				//为空证明是第一个评审的专家 可以直接保存
				expertScore.setId(WfUtil.createUUID());
				expertScore.setSupplierId(supplyMark.getSupplierId());
				BigDecimal expertValue = new BigDecimal(prarm);
				BigDecimal score2 = new BigDecimal(score);
				expertScore.setExpertValue(expertValue);
				expertScore.setScore(score2);
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}
			}
		}else{
			map.put("supplierId", expertScore.getSupplierId());
			List<ExpertScore> expertScoreList = mapper.selectByMap(map);
			if(expertScoreList!= null && expertScoreList.size()>0){
				//不为空则修改以前的为历史记录
				for (ExpertScore expertScore2 : expertScoreList) {
					if(expertScore2.getIsHistory()!=1){
						expertScore2.setIsHistory((short) 1);
						mapper.updateByPrimaryKeySelective(expertScore2);
					}
				}
				//然后在新增一条新的数据
				expertScore.setId(WfUtil.createUUID());
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}else{
				//为空证明是第一个评审的专家 可以直接保存
				expertScore.setId(WfUtil.createUUID());
				expertScore.setIsHistory((short) 0);
				mapper.insert(expertScore);
			}
		}
	}

}
