package bss.dao.ppms;

import bss.model.ppms.BidAnnouncement;

public interface BidAnnouncementMapper {
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