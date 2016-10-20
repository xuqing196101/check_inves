package ses.service.ems.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertBlackListLogMapper;
import ses.dao.ems.ExpertBlackListMapper;
import ses.dao.ems.ExpertMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertBlackList;
import ses.model.ems.ExpertBlackListLog;
import ses.service.ems.ExpertBlackListService;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

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
	 * 专家
	 */
	@Autowired
	private ExpertMapper expertMapper;
	
	/**
	 * 操作记录
	 */
	@Autowired
	private ExpertBlackListLogMapper expertBlackListHistoryMapper;
	
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
	 * @Title: updateStatus
	 * @author Xu Qing
	 * @date 2016-9-9 下午4:51:25  
	 * @Description: 根据id更新状态信息  
	 * @param @param id      
	 * @return void
	 */
	@Override
	public void updateStatus(ExpertBlackList expertBlackList,ExpertBlackListLog expertBlackListLog,String[] ids) {
		for(int i = 0;i<ids.length;i++){
			expertBlackList.setId(ids[i]);
			mapper.updateStatus(expertBlackList);
			//记录操作
			expertBlackList=this.findById(ids[i]);
			expertBlackListLog.setOperationDate(new Date()); 
			expertBlackListLog.setExpertId(expertBlackList.getRelName());
			expertBlackListLog.setOperator("我");
			expertBlackListLog.setOperationType("2");
			expertBlackListLog.setDateOfPunishment(expertBlackList.getDateOfPunishment());
			expertBlackListLog.setPunishDate(expertBlackList.getPunishDate());
			expertBlackListLog.setPunishType(expertBlackList.getPunishType());
			expertBlackListLog.setReason(expertBlackList.getReason());
			
			this.insertHistory(expertBlackListLog);
		}
	}
	
	/**
	 * @Title: findExpertList
	 * @author Xu Qing
	 * @date 2016-9-29 上午9:28:26  
	 * @Description: 查询所有专家
	 * @param @return      
	 * @return List<Expert>
	 */
	@Override
	public List<Expert> findExpertList() {
		
		return expertMapper.findExpertList();
	}
	
	/**
     * @Title: findExpertAll
     * @author Xu Qing
     * @date 2016-10-12 下午7:42:52  
     * @Description: 查询专家 ,可条件查询
     * @param @return      
     * @return List<Expert>
     */
	@Override
	public List<Expert> findExpertAll(Expert expert, Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		
		return expertMapper.findExpertAll(expert);
	}
	
	/**
     * @Title: insertHistory
     * @author Xu Qing
     * @date 2016-10-13 下午6:28:32  
     * @Description: 操作记录 
     * @param @param expertBlackListHistory      
     * @return void
     */
	@Override
	public void insertHistory(ExpertBlackListLog expertBlackListHistory) {
		expertBlackListHistoryMapper.insertHistory(expertBlackListHistory);
		
	}

	/**
	 * @Title: findBlackListLog
	 * @author Xu Qing
	 * @date 2016-10-14 下午2:54:03  
	 * @Description: 查询历史记录 
	 * @param @return      
	 * @return List<ExpertBlackListLog>
	 */
	@Override
	public List<ExpertBlackListLog> findBlackListLog(ExpertBlackListLog expertBlackListHistory,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		return expertBlackListHistoryMapper.findBlackListLog(expertBlackListHistory);
	}

}
