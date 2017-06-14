package bss.service.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;

public interface ScoreModelService {
	public List<ScoreModel> findListByScoreModel(ScoreModel scoreModel);
	public int saveScoreModel(ScoreModel scoreModel);
	public int updateScoreModel(ScoreModel scoreModel);
	public int delScoreModelByid(HashMap<String, Object> map);
	public int delScoreModelByMap(HashMap<String, Object> map);//朱巷删除   
	public int delSoftScoreModelByid(HashMap<String, Object> map);
	public ScoreModel findScoreModelByScoreModel(ScoreModel scoreModel);
	public void deleteScoreModelByMtId(String id);
  /**
   *〈简述〉根据创建时间倒序查询
   *〈详细描述〉
   * @author Ye MaoLin
   * @param scoreModel
   * @return
   */
  public List<ScoreModel> findListByScoreModelByTime(ScoreModel scoreModel);
	
	/*public List<MarkTerm> findListByMarkTerm(MarkTerm markTerm);
	public int saveMarkTerm(MarkTerm markTerm);
	public int updateMarkTerm(MarkTerm markTerm);
	public int delMarkTermByid(HashMap<String, Object> map);
	public int delMarkTermByMap(HashMap<String, Object> map);
	public int delSoftMarkTermByid(HashMap<String, Object> map);
	public int insert(MarkTerm markTerm);
	
	
	public List<ParamInterval> findListByParamInterval(ParamInterval paramInterval);
	public int saveParamInterval(ParamInterval paramInterval);
	public int updateParamInterval(ParamInterval paramInterval);
	public int delParamIntervalByid(HashMap<String, Object> map);
	public int delSoftParamIntervalByid(HashMap<String, Object> map);
	public int delParamIntervalByMap(HashMap<String, Object> map);*/
}
