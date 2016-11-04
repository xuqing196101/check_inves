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
    void insertStationMessage(StationMessage stationMessage);

    /**
     * @Description:修改站内消息
     *
     * @author Wang Wenshuai
     * @date 2016年9月8日 下午5:17:15  
     * @param @param stationMessage      
     * @return void
     */
    void updateStationMessage(StationMessage stationMessage);

    /**
     * @Description:分页获取集合
     *
     * @author Wang Wenshuai
     * @date 2016年9月8日 下午5:17:51  
     * @param @param stationMessage
     * @param @return      
     * @return List<StationMessage>
     */
    List<StationMessage> listStationMessage(StationMessage stationMessage, Integer pageNum);



    /**
     * 
     *〈简述〉修改状态为已完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param message 通知
     */
    void updateIsFinish(StationMessage message);


}
