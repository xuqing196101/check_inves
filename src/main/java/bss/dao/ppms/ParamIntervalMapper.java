package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;

public interface ParamIntervalMapper {
	public List<ParamInterval> findListByParamInterval(ParamInterval paramInterval);
	public int saveParamInterval(ParamInterval paramInterval);
	public int updateParamInterval(ParamInterval paramInterval);
	public int delParamIntervalByid(HashMap<String, Object> map);
	public int delSoftParamIntervalByid(HashMap<String, Object> map);
	public int delParamIntervalByMap(HashMap<String, Object> map);
}
