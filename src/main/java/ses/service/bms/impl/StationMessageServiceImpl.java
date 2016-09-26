/**
 * 
 */
package ses.service.bms.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.StationMessageMapper;
import ses.model.bms.StationMessage;
import ses.service.bms.StationMessageService;

/**
 * @Description:站内消息实现service
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午5:21:58
 * @since  JDK 1.7
 */
@Service("StationMessageService")
public class StationMessageServiceImpl implements StationMessageService {

	@Autowired
	private StationMessageMapper stationMessageMapper;


	/**
	 * @Description:插入站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:14:04  
	 * @param @param stationMessage     
	 * @return void
	 */
	public void insertStationMessage(StationMessage stationMessage) {
		
		stationMessageMapper.insert(stationMessage);
		
	}

	/**
	 * @Description:修改|插入 站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:15  
	 * @param @param stationMessage      
	 * @return void
	 */
	public void updateStationMessage(StationMessage stationMessage) {
		
		if(stationMessage.getId()!=null&&!"".equals(stationMessage.getId())){
			
			stationMessageMapper.updateByPrimaryKeySelective(stationMessage);
			
		}else{
			stationMessageMapper.insert(stationMessage);
			
		}
		
	}

	/**
	 * @Description:分页获取集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:51  
	 * @param @param stationMessage
	 * @param @return      
	 * @return List<StationMessage>
	 */
	public List<StationMessage> listStationMessage(StationMessage stationMessage) {
		
		PageHelper.startPage(stationMessage.getPageNum(),stationMessage.getPageSize());
		
		return stationMessageMapper.listStationMessage(stationMessage);
	}

	/**
	 * @Description:根据id获取单个消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:18:00  
	 * @param @param id      
	 * @return StationMessage
	 */
	public StationMessage showStationMessage(String id) {

		return stationMessageMapper.selectByPrimaryKey(id);

	}

	/**
	 * @Description:发布 or 撤回 消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:20:53  
	 * @param @param id      
	 * @return void
	 */
	public void updateSMIsIssuance(String id,String isIssuance) {
		
		stationMessageMapper.updateByPrimaryKeySelective(new StationMessage(id, new Short(isIssuance), null));
		
	}

	/**
	 * @Description: 软删除一条记录
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:21:18  
	 * @param @param id      
	 * @return void
	 */
	public void deleteSoftSMIsDelete(String id) {
		
		stationMessageMapper.updateByPrimaryKeySelective(new StationMessage(id, new Short("1")));
		
	}

}
