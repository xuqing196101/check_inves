package ses.service.bms.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.bms.AreaMapper;
import ses.model.bms.Area;
import ses.model.bms.AreaZtree;
import ses.service.bms.AreaServiceI;

/**
 * 
 * @Title:AreaServiceImpl
 * @Description:地区管理业务逻辑类
 * @author FengTian
 * @date 2016-9-7下午6:25:47
 */
@Service("areaService")
public class AreaServiceImpl implements AreaServiceI {
    
    /** 区域根节点 */
    private static final String ROOT_PID = "0";
    
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
/*	@Override
	public void save(Area area) {
	    Area aa = null;
        if(area.getId() != null && !"".equals(area.getId())){
            aa = areaMapper.selectById(area.getId());
            area.setParentId(aa.getId());
        }else{
            area.setParentId(ROOT_PID);
        }
        area.setIsDeleted(0);
        area.setCreatedAt(new Date());
		areaMapper.save(area);
	}*/
	
	@Override
    public void save(Area area) {
        if(area.getId() != null && !"".equals(area.getId())){
            Area aa = areaMapper.selectById(area.getId());
            List<Area> areas = areaMapper.findAreaByParentId(aa.getId());
            if(areas != null && areas.size() > 0){
                for (Area area2 : areas) {
                    if(area.getName().equals(area2.getName())){
                        area2.setIsDeleted(0);
                        areaMapper.update(area2);
                    }
                }
            }
           
            //area.setParentId(aa.getId());
        }else{
            area.setParentId(ROOT_PID);
            area.setIsDeleted(0);
            area.setCreatedAt(new Date());
            areaMapper.save(area);
        }
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
	public List<Area> listByArea(Area area) {
		return areaMapper.listByArea(area);
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
	public List<Area> findTreeByPid(String pid, String name) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pid", pid);
		map.put("name", name);
		if (StringUtils.isNotBlank(pid) || StringUtils.isNotBlank(name)) {
			List<Area> list = areaMapper.findTreeByPid(map);
			return list;
		}
		
		return new ArrayList<Area>();
	}
	
	
	/**
	 * 
	 * @see ses.service.bms.AreaServiceI#getTreeList(java.lang.String, java.lang.String)
	 */
	@Override
    public List<AreaZtree> getTreeList(String pid, String name) {
	    Map<String,Object> map = new HashMap<String,Object>();
	    
	    if (StringUtils.isNotBlank(name)) {
	        map.put("name", name);
	        List<Area> areaList = areaMapper.findTreeByPid(map);
	        return synchrous(areaList);
	    } else {
	        if (!StringUtils.isNotBlank(pid)) {
	            map.put("pid", ROOT_PID);
	        } else {
	            map.put("pid", pid);
	        }
	        
	        List<Area> areaList = areaMapper.findTreeByPid(map);
	        return asynchrous(areaList);
	    }
	   
    }
	
	/**
	 * 
	 *〈简述〉
	 *  异步查询
	 *〈详细描述〉
	 * @author myc
	 * @param areaList List<Area>集合
	 * @return
	 */
	private List<AreaZtree> asynchrous(List<Area> areaList){
	    List<AreaZtree> list = new ArrayList<AreaZtree>();
	    for (Area a : areaList) {
            AreaZtree az = new AreaZtree();
            Map<String, Object> map = new HashMap<>();
            map.put("pid", a.getId());
            List<Area> ziList = areaMapper.findTreeByPid(map );
            if(ziList!=null && ziList.size()>0){
                az.setIsParent("true");
            }else{
                az.setIsParent("false");
            }
            az.setId(a.getId());
            az.setName(a.getName());
            az.setpId(a.getParentId());
            list.add(az);
        }
	    return list;
	}
	/**
	 * 
	 *〈简述〉
	 *  同步查询
	 *〈详细描述〉
	 * @author myc
	 * @param areaList List<Area>
	 * @return
	 */
	private List<AreaZtree> synchrous(List<Area> areaList){
        List<AreaZtree> list = new ArrayList<AreaZtree>();
        for (Area a : areaList) {
            AreaZtree az = new AreaZtree();
            for (Area area : areaList) {
                if (a.getId().equals(area.getParentId())) {
                    az.setIsParent("true");
                } else {
                    az.setIsParent("false");
                }
            }
            az.setId(a.getId());
            az.setName(a.getName());
            az.setpId(a.getParentId());
            list.add(az);
        }
        return list;
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
	
	/**
	 * @Title: findRootArea
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 上午11:01:14
	 * @Description: 查询所有根节点
	 * @param: @return
	 * @return: List<Area>
	 */
	@Override
	public List<Area> findRootArea() {
		return areaMapper.findRootArea("0");
	}
	
	/**
	 * @Title: findAreaByParentId
	 * @author: Wang Zhaohua
	 * @date: 2016-10-13 上午11:01:25
	 * @Description: 根据父节点 ID 查询子节点
	 * @param: @param id
	 * @param: @return
	 * @return: List<Area>
	 */
	@Override
	public List<Area> findAreaByParentId(String id) {
		return areaMapper.findAreaByParentId(id);
	}

	/**
	 * 
	 * Description: 通过省名称模糊查询地区ID
	 * 
	 * @author Easong
	 * @version 2017年6月1日
	 * @param name
	 * @return
	 */
	@Override
	public String selectByName(String name) {
		return areaMapper.selectByName(name);
	}

}
