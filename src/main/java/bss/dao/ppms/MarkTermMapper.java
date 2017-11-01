package bss.dao.ppms;

import java.util.HashMap;
import java.util.List;

import bss.model.ppms.MarkTerm;
import bss.model.ppms.ParamInterval;
import bss.model.ppms.ScoreModel;

public interface MarkTermMapper {
	public List<MarkTerm> findListByMarkTerm(MarkTerm markTerm);
	public int saveMarkTerm(MarkTerm markTerm);
	public int updateMarkTerm(MarkTerm markTerm);
	public int delMarkTermByid(HashMap<String, Object> map);
	public int delMarkTermByMap(HashMap<String, Object> map);
	public int delSoftMarkTermByid(HashMap<String, Object> map);
	public int insert(MarkTerm markTerm);
	public int delMarkTermByParentId(HashMap<String, Object> map);
	public MarkTerm findMarkTermById(String id);
	public List<MarkTerm> findMarkTermByPackageId(MarkTerm markTerm);
}
