package bss.dao.ob;

import bss.model.ob.OBSpecialDate;
import bss.model.ob.OBSpecialDateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBSpecialDateMapper {
    int countByExample(OBSpecialDateExample example);

    int deleteByExample(OBSpecialDateExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBSpecialDate record);

    int insertSelective(OBSpecialDate record);

    List<OBSpecialDate> selectByExample(OBSpecialDateExample example);

    OBSpecialDate selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBSpecialDate record, @Param("example") OBSpecialDateExample example);

    int updateByExample(@Param("record") OBSpecialDate record, @Param("example") OBSpecialDateExample example);

    int updateByPrimaryKeySelective(OBSpecialDate record);

    int updateByPrimaryKey(OBSpecialDate record);
}