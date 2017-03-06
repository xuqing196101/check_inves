package bss.dao.ob;

import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBProjectResultMapper {
    int countByExample(OBProjectResultExample example);

    int deleteByExample(OBProjectResultExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProjectResult record);

    int insertSelective(OBProjectResult record);

    List<OBProjectResult> selectByExample(OBProjectResultExample example);

    OBProjectResult selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByExample(@Param("record") OBProjectResult record, @Param("example") OBProjectResultExample example);

    int updateByPrimaryKeySelective(OBProjectResult record);

    int updateByPrimaryKey(OBProjectResult record);
}