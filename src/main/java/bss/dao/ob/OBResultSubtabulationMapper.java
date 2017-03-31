package bss.dao.ob;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bss.model.ob.OBResultSubtabulation;

/**
 * 
* @ClassName: OBResultSubtabulationMapper 
* @Description: 竞价结果子表Mapper
* @author Easong
* @date 2017年3月28日 下午8:15:51 
*
 */
public interface OBResultSubtabulationMapper {
	
	/**
	 * 
	* @Title: deleteByPrimaryKey 
	* @Description: 根据主键删除
	* @author Easong
	* @param @param id
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
    int deleteByPrimaryKey(String id);

    int insert(OBResultSubtabulation record);

    int insertSelective(OBResultSubtabulation record);

    OBResultSubtabulation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OBResultSubtabulation record);

    int updateByPrimaryKey(OBResultSubtabulation record);
    
    /**
     * 
     * Description: 根据竞价信息查询
     * 
     * @author  zhang shubin
     * @version  2017年3月30日 
     * @param  @param projectId
     * @param  @return 
     * @return List<OBResultSubtabulation> 
     * @exception
     */
    List<OBResultSubtabulation> selectByProjectId(@Param("projectId") String projectId);
}