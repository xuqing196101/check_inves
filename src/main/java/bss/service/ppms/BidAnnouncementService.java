package bss.service.ppms;

import java.util.List;

import bss.model.ppms.BidAnnouncement;
import bss.model.ppms.Project;

/**

* @Title:BidAnnouncementService 
* @Description: 招标公告实现接口
* @author Peng Zhongjun
* @date 2016-10-9下午8:23:29
 */
public interface BidAnnouncementService {
	
	/**
	* @Title: selectBidAnnouncementById
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:20:37  
	* @Description: 根据ID获取招标公告 
	* @param @param id
	* @param @return      
	* @return BidAnnouncement
	 */
	public BidAnnouncement selectBidAnnouncementById(String id);
	
	/**
	 
	* @Title: insertSelective
	* @author Peng Zhongjun
	* @date 2016-10-9 下午8:17:17  
	* @Description: 插入一条数据 
	* @param @param bidAnnouncement      
	* @return void
	 */
	public void insertSelective(BidAnnouncement bidAnnouncement);
}
