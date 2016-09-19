package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.ems.ExpertBlackListMapper;
import ses.model.ems.ExpertBlackList;
import ses.service.ems.ExpertBlackListService;
import ses.util.PropertiesUtil;

/**
 * <p>Title:ExpertBlackListServiceImpl </p>
 * <p>Description: 专家黑名单控制类</p>
 * @author Xu Qing
 * @date 2016-9-9下午5:07:28
 */
@Service("expertBlackListService")
public class ExpertBlackListServiceImpl implements ExpertBlackListService{
	@Autowired
	private ExpertBlackListMapper mapper;
	
	/**
	 * @Title: insert
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:38:48  
	 * @Description: 新增黑名单 
	 * @param @param expertBlackList      
	 * @return void
	 */
	@Override
	public void insert(ExpertBlackList expertBlackList) {
		mapper.insertSelective(expertBlackList);
		
	}
	/**
	 * @Title: update
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:41:47  
	 * @Description: 修改黑名单
	 * @param @param expertBlackList      
	 * @return void
	 */
	@Override
	public void update(ExpertBlackList expertBlackList) {
		mapper.updateByPrimaryKeySelective(expertBlackList);
		
	}
	/**
	 * @Title: findList
	 * @author Xu Qing
	 * @date 2016-9-8 下午2:42:58  
	 * @Description: 查询黑名单,可条件查询
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	@Override
	public List<ExpertBlackList> findAll(ExpertBlackList expertBlackList,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		Map map = new HashMap();
		if(expertBlackList!=null){
			map.put("relName", expertBlackList.getRelName());
			map.put("punishType", expertBlackList.getPunishType());
			map.put("punishDate", expertBlackList.getPunishDate());
		}else{
			map.put("relName", null);
			map.put("punishType", null);
			map.put("punishDate", null);
		}
		return mapper.findList(map);
	}
	/**
	 * @Title: findById
	 * @author Xu Qing
	 * @date 2016-9-9 下午2:09:05  
	 * @Description: 
	 * @param @param id
	 * @param @return      
	 * @return ExpertBlackList
	 */
	@Override
	public ExpertBlackList findById(String id) {
		
		return mapper.selectByPrimaryKey(id);
	}
	/**
	 * @Title: delete
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:51:25  
	 * @Description: 根据id删除信息 
	 * @param @param id      
	 * @return void
	 */
	@Override
	public void delete(String id) {
		mapper.deleteByPrimaryKey(id);
		
	}


}
