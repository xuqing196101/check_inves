package bss.service.ppms.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ppms.ScoreModelMapper;
import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
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
	
	public void deleteScoreModelByMtId(String id) {
        scoreModelMapper.deleteScoreModelByMtId(id);
    }
	@Override
	public int delSoftScoreModelByid(HashMap<String, Object> map) {
		return scoreModelMapper.delSoftScoreModelByid(map);
	}
	@Override
	public int delScoreModelByMap(HashMap<String, Object> map) {
		return scoreModelMapper.delScoreModelByMap(map);
	}
	/*@Override
	public List<MarkTerm> findListByMarkTerm(MarkTerm markTerm) {
		return scoreModelMapper.findListByMarkTerm(markTerm);
	}

	@Override
	public int saveMarkTerm(MarkTerm markTerm) {
		return scoreModelMapper.saveMarkTerm(markTerm);
	}

	@Override
	public int updateMarkTerm(MarkTerm markTerm) {
		return scoreModelMapper.updateMarkTerm(markTerm);
	}

	@Override
	public int delMarkTermByid(HashMap<String, Object> map) {
		return scoreModelMapper.delMarkTermByid(map);
	}

	@Override
	public int delMarkTermByMap(HashMap<String, Object> map) {
		return scoreModelMapper.delMarkTermByMap(map);
	}

	@Override
	public int delSoftMarkTermByid(HashMap<String, Object> map) {
		return scoreModelMapper.delSoftMarkTermByid(map);
	}

	@Override
	public List<ParamInterval> findListByParamInterval(
			ParamInterval paramInterval) {
		return scoreModelMapper.findListByParamInterval(paramInterval);
	}

	@Override
	public int saveParamInterval(ParamInterval paramInterval) {
		return scoreModelMapper.saveParamInterval(paramInterval);
	}

	@Override
	public int updateParamInterval(ParamInterval paramInterval) {
		return scoreModelMapper.updateParamInterval(paramInterval);
	}

	@Override
	public int delParamIntervalByid(HashMap<String, Object> map) {
		return scoreModelMapper.delParamIntervalByid(map);
	}

	@Override
	public int delSoftParamIntervalByid(HashMap<String, Object> map) {
		return scoreModelMapper.delSoftParamIntervalByid(map);
	}

	@Override
	public int insert(MarkTerm markTerm) {
		return scoreModelMapper.insert(markTerm);
	}

	

	@Override
	public int delParamIntervalByMap(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return scoreModelMapper.delParamIntervalByMap(map);
	}*/

    @Override
    public ScoreModel findScoreModelByScoreModel(ScoreModel scoreModel) {
        return scoreModelMapper.findScoreModelByScoreModel(scoreModel);
    }

    @Override
    public List<ScoreModel> findListByScoreModelByTime(ScoreModel scoreModel) {
      return scoreModelMapper.findListByScoreModelByTime(scoreModel);
    }

}
