package ses.service.ems.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import common.utils.DateUtils;

/**
 * <p>Title:ExpertBlackListServiceImpl </p>
 * <p>Description: 专家黑名单控制类</p>
 * @author Xu Qing
 * @date 2016-9-9下午5:07:28
 */
@Service("expertBlackListService")
public class ExpertBlackListServiceImpl implements ExpertBlackListService{
	
	// 专家黑名单状态   0：处罚中  1：过期
	private static final Integer EXPERT_BLACKLIST_STATUS_0 = 0;
	private static final Integer EXPERT_BLACKLIST_STATUS_1 = 1;
	//3个月  6个月  一年  两年  三年 （字符串）
	private static final String EXPERT_BLACKLIST_TIME_LIMIT_THREE_MONTH_STR = "3个月";
	private static final String EXPERT_BLACKLIST_TIME_LIMIT_SIX_MONTH_STR = "6个月";
	private static final String EXPERT_BLACKLIST_TIME_LIMIT_ONE_YEAR_STR = "一年";
	private static final String EXPERT_BLACKLIST_TIME_LIMIT_TWO_YEAR_STR = "两年";
	private static final String EXPERT_BLACKLIST_TIME_LIMIT_THREE_YEAR_STR = "三年";
	//3个月  6个月  一年  两年  三年  （数字）
	private static final Integer EXPERT_BLACKLIST_TIME_LIMIT_THREE_MONTH_NUM = 3;
	private static final Integer EXPERT_BLACKLIST_TIME_LIMIT_SIX_MONTH_NUM = 6;
	private static final Integer EXPERT_BLACKLIST_TIME_LIMIT_ONE_YEAR_NUM = 12;
	private static final Integer EXPERT_BLACKLIST_TIME_LIMIT_TWO_YEAR_NUM = 24;
	private static final Integer EXPERT_BLACKLIST_TIME_LIMIT_THREE_YEAR_NUM = 36;
	
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
		return mapper.findList(expertBlackList);
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
			expertBlackListLog.setExpertId(expertBlackList.getExpertId());
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
		//过滤掉黑名单那中的专家
		List<ExpertBlackList> expertBlackList = mapper.findList(new ExpertBlackList());
		List<String> list = new ArrayList<String>();
		if(expertBlackList != null && expertBlackList.size()>0){
			for(int i=0;i<expertBlackList.size();i++){
				String id = expertBlackList.get(i).getExpertId();
				if(id !=null){
					list.add(id);	
				}
			}
			expert.setIds(list);
		}
		
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
		List<ExpertBlackListLog>  list= expertBlackListHistoryMapper.findBlackListLog(expertBlackListHistory);
		 return list;
	}

	/**
	 * 
	* @Title: updateExpertBlackStatus 
	* @Description: 定时修改专家黑名单处罚日期截止时的处罚状态
	* @author Easong
	* @param     设定文件 
	* @throws
	 */
	@Override
	public void updateExpertBlackStatus() {
		// 查询所有黑名单专家列表
		List<ExpertBlackList> blackListExpert = mapper.findAllBlackListExpert(EXPERT_BLACKLIST_STATUS_0);
		// 获取当前系统时间
		Date currSysTime = new Date();
		for (ExpertBlackList expertBlackList : blackListExpert) {
			// 获取专家处罚开始日期
			Date dateOfPunishment = expertBlackList.getDateOfPunishment();
			// 获取专家处罚期限时长
			String punishDate = expertBlackList.getPunishDate();
			// 计算处罚截止时间
			Date nMonthAfterDate = null;
			//3个月  6个月  一年  两年  三年
			if(EXPERT_BLACKLIST_TIME_LIMIT_THREE_MONTH_STR.equals(punishDate)){
				nMonthAfterDate = DateUtils.getNMonthAfterDate(dateOfPunishment, EXPERT_BLACKLIST_TIME_LIMIT_THREE_MONTH_NUM);
			}
			if(EXPERT_BLACKLIST_TIME_LIMIT_SIX_MONTH_STR.equals(punishDate)){
				nMonthAfterDate = DateUtils.getNMonthAfterDate(dateOfPunishment, EXPERT_BLACKLIST_TIME_LIMIT_SIX_MONTH_NUM);
			}
			if(EXPERT_BLACKLIST_TIME_LIMIT_ONE_YEAR_STR.equals(punishDate)){
				nMonthAfterDate = DateUtils.getNMonthAfterDate(dateOfPunishment, EXPERT_BLACKLIST_TIME_LIMIT_ONE_YEAR_NUM);
			}
			if(EXPERT_BLACKLIST_TIME_LIMIT_TWO_YEAR_STR.equals(punishDate)){
				nMonthAfterDate = DateUtils.getNMonthAfterDate(dateOfPunishment, EXPERT_BLACKLIST_TIME_LIMIT_TWO_YEAR_NUM);
			}
			if(EXPERT_BLACKLIST_TIME_LIMIT_THREE_YEAR_STR.equals(punishDate)){
				nMonthAfterDate = DateUtils.getNMonthAfterDate(dateOfPunishment, EXPERT_BLACKLIST_TIME_LIMIT_THREE_YEAR_NUM);
			}
			
			// 比较当前系统时间与处罚截止时间
			int compareDate = DateUtils.compareDate(currSysTime, nMonthAfterDate);
			// 当前系统时间大于等于处罚截止时间
			if(compareDate == 1 || compareDate == 0){
				// 修改状态
				expertBlackList.setStatus(EXPERT_BLACKLIST_STATUS_1);
				mapper.updateStatus(expertBlackList);
			}
		}
	}

	@Override
	public Integer yzsc(String id) {
		return mapper.yzsc(id);
	}

	@Override
	public List<ExpertBlackList> getIndexExpertBlackList() {
		PageHelper.startPage(0, 5);
		return mapper.findList(null);
	}

}
