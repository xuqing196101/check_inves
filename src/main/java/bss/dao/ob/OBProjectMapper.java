package bss.dao.ob;

import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OBProjectMapper {
    int countByExample(OBProjectExample example);

    int deleteByExample(OBProjectExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProject record);

    int insertSelective(OBProject record);

    List<OBProject> selectByExample(OBProjectExample example);

    OBProject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByExample(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByPrimaryKeySelective(OBProject record);

    int updateByPrimaryKey(OBProject record);
}