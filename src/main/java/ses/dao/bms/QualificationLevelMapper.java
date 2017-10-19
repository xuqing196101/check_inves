package ses.dao.bms;

import org.apache.ibatis.annotations.Param;

import ses.model.bms.DictionaryData;
import ses.model.bms.QualificationLevel;

import java.util.List;

/**
 * Created by yggc on 2017/2/22.
 */

public interface QualificationLevelMapper {

    public void save(QualificationLevel qualificationLevel);

    public List<QualificationLevel> findList(@Param("qualificationId")String qualificationId);
    /**
     *等级是三的
     * @returnde
     */
    public List<QualificationLevel> getThird();
    
    public void delete(@Param("qualificationId")String qualificationId);

	public List<DictionaryData> getLevelByQid(String[] split);
    /**
     * 根据资质id和等级统计数量
     * @param quaId
     * @param level
     * @return
     */
	public int countByQuaIdAndLevel(@Param("quaId")String quaId, @Param("level")String level);
}
