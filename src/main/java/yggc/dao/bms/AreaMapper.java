package yggc.dao.bms;

import java.util.List;

import yggc.model.bms.Area;

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
	List<Area> listByArea();

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
	List<Area> findTreeByPid(String pid);

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

}