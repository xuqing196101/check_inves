/**
 * 
 */
package ses.service.bms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.bms.AreaMapper;
import ses.dao.bms.StationMessageMapper;
import ses.model.bms.StationMessage;
import ses.service.bms.StationMessageService;
import ses.service.bms.TodosService;
import ses.util.PropertiesUtil;

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
    @Autowired
    private TodosService todosService;


    /**
     * @Description:插入站内消息
     *
     * @author Wang Wenshuai
     * @date 2016年9月8日 下午5:14:04  
     * @param @param stationMessage     
     * @return void
     */
    public void insertStationMessage(StationMessage stationMessage) {
        
        
        stationMessageMapper.insertSelective(stationMessage);

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
    @Override
    public List<StationMessage> listStationMessage(StationMessage stationMessage,Integer pageNum) {
        List<String> permisssion = todosService.getPermisssion(stationMessage.getReceiverId());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message",stationMessage);
        map.put("permission",permisssion);
        if (pageNum==0){
            PageHelper.startPage(1, 14);
        }else{
            PropertiesUtil config = new PropertiesUtil("config.properties");
            PageHelper.startPage(pageNum, Integer.parseInt(config.getString("pageSize")));
        }
        List<StationMessage> listStationMessage = stationMessageMapper.listStationMessage(map);
        return listStationMessage;
    }


    /**
     * 
     *〈简述〉修改=
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param message 通知
     */
    @Override
    public void updateStationMessage(StationMessage stationMessage) {
        stationMessageMapper.updateByPrimaryKeySelective(stationMessage);
    }



    /**
     * 
     *〈简述〉修改状态为已完成
     *〈详细描述〉
     * @author Wang Wenshuai
     * @param message 通知
     */
    @Override
    public void updateIsFinish(StationMessage message) {
        stationMessageMapper.updateIsFinish(message);        
    }



}
