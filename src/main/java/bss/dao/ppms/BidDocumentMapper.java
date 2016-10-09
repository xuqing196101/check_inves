package bss.dao.ppms;

import bss.model.ppms.BidDocument;

public interface BidDocumentMapper {
	/**
	* @Title: selectBidDocumentById
	* @author Peng Zhongjun
	* @date 2016-10-9 上午10:54:33  
	* @Description: 根据ID获取招标文件 
	* @param @param id
	* @param @return      
	* @return BidDocument
	 */
	public BidDocument selectBidDocumentById(String id);
}