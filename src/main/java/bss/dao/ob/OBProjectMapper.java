package bss.dao.ob;

import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectExample;
import bss.model.ob.OBProjectResult;

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

    /**
     * 
     * Description: 根据主键查询竞价信息
     * 
     * @author  zhang shubin
     * @version  2017年3月11日 
     * @param  @param id
     * @param  @return 
     * @return OBProject 
     * @exception
     */
    OBProject selectByPrimaryKey(String id);
    
    int updateByExampleSelective(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByExample(@Param("record") OBProject record, @Param("example") OBProjectExample example);

    int updateByPrimaryKeySelective(OBProject record);

    int updateByPrimaryKey(OBProject record);


	List<OBProject> selectAllOBproject(Map<String, Object> map);

	List<OBProject> selectData(Map<String, Object> map);
	/**
	 * 获取竞价信息 不是暂存 和 结束竞价 
	 * @author yangHongLiang
	 */
	List<OBProject> selectByStatus();
	/**
	 * 根据id 获取暂存的信息
	 */
	OBProject selectTemporary(Map<String,Object> map);
}