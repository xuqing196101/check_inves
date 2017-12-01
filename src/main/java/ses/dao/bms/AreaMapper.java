package ses.dao.bms;

import org.apache.ibatis.annotations.Param;
import ses.model.bms.Area;

import java.util.List;
import java.util.Map;


/**
 * 
 *  @Title:AreaMapper  
 *  @Description:地区管理持久层接口 
 *  @author FengTian
 *  @date 2016-9-7下午6:12:18
 */
public interface AreaMapper {
    /**
	 * 
	 * @Title: deleteById
	 * @author FengTian
	 * @date 2016-9-7 下午6:13:26
	 * @Description: 根据id删除
	 * @param @param id
	 * @return void
	 */
	void deleteById(String id);

	/**
	 * 
	 * @Title: selectById
	 * @author FengTian
	 * @date 2016-9-7 下午6:14:02
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return
	 * @return Area
	 */
	Area selectById(String id);

	/**
	 * 
	 * @Title: listByArea
	 * @author FengTian
	 * @date 2016-9-7 下午6:14:38
	 * @Description: 查询全部
	 * @param @return
	 * @return List<Area>
	 */
	List<Area> listByArea(Area area);

	/**
	 * 
	 * @Title: findTreeByPid
	 * @author FengTian
	 * @date 2016-9-7 下午6:14:57
	 * @Description: 根据父节点查询子节点
	 * @param @param pid
	 * @param @return
	 * @return List<Area>
	 */
	List<Area> findTreeByPid(Map<String, Object> map);

	/**
	 * 
	 * @Title: save
	 * @author FengTian
	 * @date 2016-9-7 下午6:16:41
	 * @Description: 添加
	 * @param @param area
	 * @return void
	 */
	void save(Area area);

	/**
	 * 
	 * @Title: update
	 * @author FengTian
	 * @date 2016-9-7 下午6:16:58
	 * @Description: 修改
	 * @param @param record
	 * @return void
	 */
	void update(Area record);
	
	List<Area> findRootArea(String areaType);
	
	List<Area> findAreaByParentId(String id);
	
	/**
	 * 
	 * Description: 通过省名称模糊查询地区ID
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @param name
	 * @return
	 */
	String selectByName(@Param("name") String name);

	List<Area> getTreeForExt();
	
	/**
	 *
	 * Description: 级联查询地区
	 *
	 * @author Easong
	 * @version 2017/11/6
	 * @param 
	 * @since JDK1.7
	 */
	List<Area> selectOfHierarchical(Map<String, Object> map);

	List<Area> selectByParentId(Map<String, Object> map);

}