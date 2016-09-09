/**
 * 
 */
package ses.service.bms;

import java.util.List;

import ses.model.bms.StationMessage;

/**
 * @Description:站内消息
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月8日下午5:13:13
 * @since  JDK 1.7
 */
public interface StationMessageService {
	
	/**
	 * 
	 * @Description:插入站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:27:23  
	 * @param @param stationMessage      
	 * @return void
	 */
	public void insertStationMessage(StationMessage stationMessage);
	
	/**
	 * @Description:修改站内消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:15  
	 * @param @param stationMessage      
	 * @return void
	 */
	public void updateStationMessage(StationMessage stationMessage);
	
	/**
	 * @Description:分页获取集合
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:17:51  
	 * @param @param stationMessage
	 * @param @return      
	 * @return List<StationMessage>
	 */
	public List<StationMessage> listStationMessage(StationMessage stationMessage);
	
	/**
	 * @Description:根据id获取单个消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:18:00  
	 * @param @param id      
	 * @return StationMessage
	 */
	public StationMessage showStationMessage(String id);
	
	/**
     * @Description:发布 or 撤回 消息
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:20:53  
	 * @param @param id      
	 * @return void
	 */
	public void updateSMIsIssuance(String id,String isIssuance);
	
	/**
	 * @Description:
	 *
	 * @author Wang Wenshuai
	 * @date 2016年9月8日 下午5:21:18  
	 * @param @param id      
	 * @return void
	 */
	public void deleteSoftSMIsDelete(String id);
}
