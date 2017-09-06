package ses.service.bms;


import java.util.List;

import ses.model.bms.DictionaryData;
import ses.model.bms.QualificationLevel;

/**
 * Created by yggc on 2017/2/22.
 */
public interface QualificationLevelService {
	
    public List<QualificationLevel> findList(Integer pageNum, String qualificationId );

    public void save(String id,String qualificationId,String grade);


    
    public List<DictionaryData> getByQuaId(String id);
    
    public void add(QualificationLevel qualificationLevel);
    
    public List<QualificationLevel> queryByQId(String qid);
    
    public void deleteByQuaId(String quaId);

	public List<DictionaryData> getLevelByQid(String qid);
}
