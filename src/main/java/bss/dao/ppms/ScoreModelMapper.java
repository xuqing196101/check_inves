package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.ScoreModel;

public interface ScoreModelMapper {
	public List<ScoreModel> findListByScoreModel(ScoreModel scoreModel);
	public int saveScoreModel(ScoreModel scoreModel);
	public int updateScoreModel(ScoreModel scoreModel);
	public int delScoreModelByid(HashMap<String, Object> map);
	public int delSoftScoreModelByid(HashMap<String, Object> map);
}
