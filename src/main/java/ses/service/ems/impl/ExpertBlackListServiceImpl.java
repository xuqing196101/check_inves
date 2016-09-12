package ses.service.ems.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertBlackListMapper;
import ses.model.ems.ExpertBlackList;
import ses.service.ems.ExpertBlackListService;

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
	 * @Description: 查询黑名单 
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	@Override
	public List<ExpertBlackList> findAll() {
		
		return mapper.findList();
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
	/**
	 * @Title: query
	 * @author Xu Qing
	 * @date 2016-9-12 下午3:22:31  
	 * @Description: 条件查询 
	 * @param @param expertBlackList
	 * @param @return      
	 * @return List<ExpertBlackList>
	 */
	@Override
	public List<ExpertBlackList> query(ExpertBlackList expertBlackList) {
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
		return mapper.query(map);
	}

}
