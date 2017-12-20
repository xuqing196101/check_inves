package ses.dao.bms;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import ses.model.bms.ContinentNationRel;
import ses.model.bms.ContinentNationRelExample;

public interface ContinentNationRelMapper {
    int countByExample(ContinentNationRelExample example);

    int deleteByExample(ContinentNationRelExample example);

    int deleteByPrimaryKey(String id);

    int insert(ContinentNationRel record);

    int insertSelective(ContinentNationRel record);

    List<ContinentNationRel> selectByExample(ContinentNationRelExample example);

    ContinentNationRel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ContinentNationRel record, @Param("example") ContinentNationRelExample example);

    int updateByExample(@Param("record") ContinentNationRel record, @Param("example") ContinentNationRelExample example);

    int updateByPrimaryKeySelective(ContinentNationRel record);

    int updateByPrimaryKey(ContinentNationRel record);
}