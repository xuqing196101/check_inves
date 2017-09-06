package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.QualificationLevelMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.QualificationLevel;
import ses.service.bms.QualificationLevelService;
import ses.util.DictionaryDataUtil;

/**
 * Created by yggc on 2017/2/22.
 */

@Service
public class QualificationLevelServiceImpl  implements QualificationLevelService {

    @Autowired
    private QualificationLevelMapper qualificationLevelMapper;
    /**
     *
     * @see ses.service.bms.QualificationLevelService#findList(java.lang.Integer, ses.model.bms.QualificationLevel)
     */
    @Override
    public List<QualificationLevel> findList(Integer pageNum, String qualificationId) {
        if (pageNum == null){
            pageNum = 1;
        }
        return qualificationLevelMapper.findList(qualificationId);
    }
    /**
     *
     * @see ses.service.bms.QualificationLevelService#save(java.lang.String, java.lang.String, java.lang.String)
     */
     
	@Override
	public void save(String id, String qualificationId, String grade) {
		// TODO Auto-generated method stubz
		
	}
	@Override
	public List<DictionaryData> getByQuaId(String id) {
		List<DictionaryData> lis1=new ArrayList<DictionaryData>();
		List<QualificationLevel> list = qualificationLevelMapper.findList(id);
		for(QualificationLevel q:list){
			DictionaryData data = DictionaryDataUtil.findById(q.getGrade());
			lis1.add(data);
		}
		return lis1;
	}
	@Override
	public void add(QualificationLevel qualificationLevel) {
		qualificationLevelMapper.save(qualificationLevel);
		
	}
	@Override
	public List<QualificationLevel> queryByQId(String qid) {
		// TODO Auto-generated method stub
		return qualificationLevelMapper.findList(qid);
	}
	@Override
	public void deleteByQuaId(String quaId) {
		 
		qualificationLevelMapper.delete(quaId);
	}
	@Override
	public List<DictionaryData> getLevelByQid(String qid) {
		
		return qualificationLevelMapper.getLevelByQid(qid.split(","));
	}
}
