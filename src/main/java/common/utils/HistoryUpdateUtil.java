package common.utils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

import common.model.UpdateHistory;
import common.service.UpdateHistoryService;
/**
 * 
 * @Title: HistoryUpdateUtil
 * @Description:  记录修改历史的工具类（插入，查询）
 * @author Li Xiaoxiao
 * @date  2017年2月22日,上午9:57:53
 *
 */
public class HistoryUpdateUtil {

	@Autowired
	private UpdateHistoryService historyUpdateService;
	/**
	 * 
	* @Title: queryUpdateID
	* @Description: TODO 
	* author: Li Xiaoxiao 
	* @param @param updateId
	* @param @return     
	* @return List<UpdateHistory>     
	* @throws
	 */
	public List<UpdateHistory> queryUpdateID(String updateId){
		
		return historyUpdateService.queryByUpdateId(updateId);
	}
	/**
	 * 
	* @Title: add
	* @Description: 记录修改的历史记录
	* author: Li Xiaoxiao 
	* @param @param updateId
	* @param @param obj     
	* @return void     
	* @throws
	 */
	public void add(String updateId,Object obj){
		UpdateHistory uh=new UpdateHistory();
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		uh.setId(id);
		uh.setUpdateId(updateId);
		String  object = JSON.toJSONString(obj);
		uh.setObject(object);
		uh.setCreateAt(new Date());
		Integer max = historyUpdateService.getMax(updateId);
		max+=1;
		uh.setTimes(max);
	//	historyUpdateService.add(uh);
	}
	/**
	 * 
	* @Title: getLast
	* @Description: 得到最后一次修改的记录 
	* author: Li Xiaoxiao 
	* @param @param updateId
	* @param @return     
	* @return UpdateHistory     
	* @throws
	 */
	public UpdateHistory getLast(String updateId){
		return historyUpdateService.getLast(updateId);
	}
	
}
