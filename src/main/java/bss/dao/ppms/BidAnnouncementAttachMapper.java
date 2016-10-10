package bss.dao.ppms;

import java.util.List;

import bss.model.ppms.BidAnnouncementAttach;

public interface BidAnnouncementAttachMapper {
	/**
	 
	* @Title: selectByPrimaryKey
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:06:27  
	* @Description: 根据ID查询 
	* @param @param id
	* @param @return      
	* @return BidAnnouncementAttach
	 */
	public BidAnnouncementAttach selectByPrimaryKey(String id);
	/**
	 * 
	* @Title: selectBidAnAttachments
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:15:48  
	* @Description: 根据招标公告Id获取附件列表 
	* @param @param annoucementId
	* @param @return      
	* @return List<BidAnnouncementAttach>
	 */
	public List<BidAnnouncementAttach> selectBidAnAttachments(String annoucementId);
	/**
	 * 
	* @Title: softDeleteAtta
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:16:01  
	* @Description: 假删除 
	* @param @param id      
	* @return void
	 */
	public void softDeleteAtta(String id);
	/**
	* @Title: insertSelective
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:16:29  
	* @Description: 插入一条记录 
	* @param @param bidAnnouncement      
	* @return void
	 */
	public void insertSelective(BidAnnouncementAttach bidAnnouncement);
	/**
	* @Title: updateByPrimaryKeySelective
	* @author Peng Zhongjun
	* @date 2016-10-9 上午11:16:35  
	* @Description: 更新附件表 
	* @param @param bidAnnouncement      
	* @return void
	 */
	public void updateByPrimaryKeySelective(BidAnnouncementAttach bidAnnouncement);
}