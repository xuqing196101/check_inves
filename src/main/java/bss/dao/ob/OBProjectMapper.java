package bss.dao.ob;

import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OBProjectMapper {
    int countByExample(OBProjectExample example);

    int deleteByExample(OBProjectExample example);

    int deleteByPrimaryKey(String id);

    int insert(OBProject record);

    int insertSelective(OBProject record);

    List<OBProject> selectByExample(OBProjectExample example);
    
    List<OBProject> selectPageList(OBProject project);

    OBProject selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByExample(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByPrimaryKeySelective(OBProject record);

    int updateByPrimaryKey(OBProject record);


	List<OBProject> selectAllOBproject(Map<String, Object> map);

  
    

}