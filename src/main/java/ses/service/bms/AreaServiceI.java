package ses.service.bms;

import java.util.List;

import ses.model.bms.Area;


/**
 * 
 *  @Title:AreaServiceI   
 *  @Description:地区管理业务逻辑接口  
 *  @author FengTian
 *  @date 2016-9-7下午6:18:42
 */
public interface AreaServiceI {
	/**
	 * 
	 * @Title: deleteById
	 * @author FengTian
	 * @date 2016-8-31 下午5:05:45
	 * @Description: 根据id删除
	 * @param @param id
	 * @return void
	 */
	void deleteById(String id);

	/**
	 * 
	 * @Title: save
	 * @author FengTian
	 * @date 2016-8-31 下午5:12:16
	 * @Description: 新增
	 * @param @param area
	 * @return void
	 */
	void save(Area area);

	/**
	 * 
	 * @Title: listById
	 * @author FengTian
	 * @date 2016-8-31 下午5:17:03
	 * @Description: 根据id查询
	 * @param @param id
	 * @param @return
	 * @return Area
	 */
	Area listById(String id);

	/**
	 * 
	 * @Title: listByArea
	 * @author FengTian
	 * @date 2016-8-31 下午5:19:31
	 * @Description: 查询全部
	 * @param @param area
	 * @param @return
	 * @return List<Area>
	 */
	List<Area> listByArea(Area area);

	/**
	 * 
	 * @Title: findTreeByPid
	 * @author FengTian
	 * @date 2016-8-31 下午5:25:23
	 * @Description: 根据父节点找出子节点
	 * @param @param pid
	 * @param @return
	 * @return List<Area>
	 */
	List<Area> findTreeByPid(String pid,String name);

	/**
	 * 
	 * @Title: update
	 * @author FengTian
	 * @date 2016-8-31 下午5:28:54
	 * @Description: 修改
	 * @param @param area
	 * @return void
	 */
	void update(Area area);
}
