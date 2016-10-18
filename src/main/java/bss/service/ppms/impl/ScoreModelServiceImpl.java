package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ScoreModelMapper;
import bss.model.ppms.ScoreModel;
import bss.service.ppms.ScoreModelService;
@Service("scoreModelService")
public class ScoreModelServiceImpl implements ScoreModelService{
	@Autowired
	private ScoreModelMapper scoreModelMapper;

	@Override
	public List<ScoreModel> findListByScoreModel(ScoreModel scoreModel) {
		return scoreModelMapper.findListByScoreModel(scoreModel);
	}

	@Override
	public int saveScoreModel(ScoreModel scoreModel) {
		return scoreModelMapper.saveScoreModel(scoreModel);
	}

	@Override
	public int updateScoreModel(ScoreModel scoreModel) {
		return scoreModelMapper.updateScoreModel(scoreModel);
	}

	@Override
	public int delScoreModelByid(HashMap<String, Object> map) {
		return scoreModelMapper.delScoreModelByid(map);
	}

	@Override
	public int delSoftScoreModelByid(HashMap<String, Object> map) {
		return scoreModelMapper.delSoftScoreModelByid(map);
	}

}
