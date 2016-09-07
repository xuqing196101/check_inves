package yggc.service.bms.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yggc.dao.bms.AreaMapper;
import yggc.model.bms.Area;
import yggc.service.bms.AreaServiceI;
/**
 * 
 * @Title:AreaServiceImpl
 * @Description:地区管理业务逻辑类
 * @author FengTian
 * @date 2016-9-7下午6:25:47
 */
@Service("areaService")
public class AreaServiceImpl implements AreaServiceI {
	@Autowired
	private AreaMapper areaMapper;
	
	/**
	 * 
	 * @Title: deleteById
	 * @author FengTian
	 * @date 2016-8-31 下午5:05:45
	 * @Description: 根据id删除
	 * @param @param id
	 * @return void
	 */
	@Override
	public void deleteById(String id) {
		 areaMapper.deleteById(id);
	}
	
	/**
	 * 
	 * @Title: save
	 * @author FengTian
	 * @date 2016-8-31 下午5:12:16
	 * @Description: 新增
	 * @param @param area
	 * @return void
	 */
	@Override
	public void save(Area area) {
		areaMapper.save(area);
	}
	
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
	@Override
	public Area listById(String id) {
		return areaMapper.selectById(id);
	}
	
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
	@Override
	public List<Area> listByArea() {
		return areaMapper.listByArea();
	}

	/**
	 * 
	 * @Title: findTreeByPid
	 * @author FengTian
	 * @date 2016-8-31 下午5:25:23
	 * @Description: 根据父节点找出子节点
	 * @param @param pid
	 * @param @return lists
	 * @return List<Area>
	 */
	@Override
	public List<Area> findTreeByPid(String pid) {
		List<Area> list = areaMapper.findTreeByPid(pid);
		List<Area> lists = new ArrayList<Area>();
		if(list.isEmpty()){
			return lists;
		}
		for(int i = 0;i < list.size();i++){
			String id = list.get(i).getId();
			Area aa = areaMapper.selectById(id);
		
			lists.add(aa);
		}
		return lists;
	}
	
	/**
	 * 
	 * @Title: update
	 * @author FengTian
	 * @date 2016-8-31 下午5:28:54
	 * @Description: 修改
	 * @param @param area
	 * @return void
	 */
	@Override
	public void update(Area area) {
		areaMapper.update(area);
	}

}
